package gnoolson.saturday.script.application;

import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.events.ScriptUpdatedEvent;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptErrorId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.entity.ScriptError;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.model.vo.DefaultField;
import gnoolson.saturday.script.model.vo.ReasonOfError;
import gnoolson.saturday.script.model.vo.Stack;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class ScriptErrorHandlerImpl implements ScriptErrorHandler {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final TimeProvider timeProvider;
    private final TransactionStarter transactionStarter;
    private final ScriptErrorRepositoryGateway scriptErrorRepositoryGateWay;
    private final PositiveNumber maxNumberOfErrorsAllowed;
    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final EventBus eventBus;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId scriptId, Exception exception, ScriptLauncher scriptLauncher) {
        Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));
        PositiveNumber numberOfErrors = scriptErrorRepositoryGateWay.count(scriptId);

        numberOfErrors = numberOfErrors.add(PositiveNumber.of(1));

        ReasonOfError reasonOfError = ReasonOfError.of(ExceptionUtils.getMessage(exception));
        Stack stack = Stack.of(ExceptionUtils.getStackTrace(exception));

        saveError(script, stack, reasonOfError, numberOfErrors);
        launchScriptErrorHandler(script, stack, reasonOfError, numberOfErrors, scriptLauncher);
    }

    /*
     *
     *
     * */
    private void saveError(Script script, Stack stack, ReasonOfError reasonOfError, PositiveNumber numberOfErrors) {
        ScriptError scriptError = new ScriptError(
                ScriptErrorId.empty(),
                script.getId(),
                timeProvider.now(),
                stack,
                reasonOfError
        );

        transactionStarter.doIt(() -> {
            scriptErrorRepositoryGateWay.save(scriptError);

            if (maxNumberOfErrorsAllowed.isLessOrEqual(numberOfErrors)) {
                script.disableAndBlock();
                scriptRepositoryGateway.save(script);

                transactionStarter.doAfterTransactionCommit(() -> {
                    eventBus.emit(new ScriptUpdatedEvent(script.getId()));
                });
            }
        });
    }

    private void launchScriptErrorHandler(Script script, Stack stack, ReasonOfError reasonOfError, PositiveNumber numberOfErrors, ScriptLauncher scriptLauncher) {
        ScriptId errorHandler = script.getErrorEventHandler();
        if (errorHandler.isEmpty())
            return;

        Optional<Script> handlerScriptOpt = scriptRepositoryGateway.find(errorHandler);
        if (!handlerScriptOpt.isPresent())
            return;

        Script handlerScript = handlerScriptOpt.get();

        Map<String, Object> args = new HashMap<>();
        args.put(DefaultField.SOURCE, "SCRIPT_ERROR");
        args.put("sourceScriptId", script.getId().getValue().toString());
        args.put("numberOfErrors", numberOfErrors.getValue());
        args.put("maxNumberOfErrorsAllowed", maxNumberOfErrorsAllowed.getValue());
        args.put("reason", reasonOfError.getValue());
        args.put("stack", stack.getValue());

        executorService.execute(() -> {
            scriptLauncher.execute(handlerScript.getId(), args);
        });
    }


}

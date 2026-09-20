package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.DeleteErrorsAndUnblockUseCase;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteErrorsAndUnblockUseCaseImpl implements DeleteErrorsAndUnblockUseCase {

    private final ScriptErrorRepositoryGateway scriptErrorRepositoryGateWay;
    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId scriptId) {
        try {
            transactionStarter.doIt(() -> {
                Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));
                script.unblock();
                scriptRepositoryGateway.save(script);
                scriptErrorRepositoryGateWay.deleteAll(scriptId);
            });
        } catch (Exception e) {
            log.warn("Exception", e);
            return false;
        }
        return true;
    }

}

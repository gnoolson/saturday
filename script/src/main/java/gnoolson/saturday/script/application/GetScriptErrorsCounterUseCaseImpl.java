package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.port.inbound.GetScriptErrorsCounterUseCase;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetScriptErrorsCounterUseCaseImpl implements GetScriptErrorsCounterUseCase {

    private final ScriptErrorRepositoryGateway scriptErrorRepositoryGateWay;

    /*
     *
     *
     * */
    @Override
    public PositiveNumber execute(ScriptId scriptId) {
        return scriptErrorRepositoryGateWay.count(scriptId);
    }

}

package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.entity.ScriptError;
import gnoolson.saturday.script.port.inbound.GetNumberOfErrorsUseCase;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetScriptErrorsUseCaseImpl implements GetNumberOfErrorsUseCase {

    private final ScriptErrorRepositoryGateway scriptErrorRepositoryGateWay;

    /*
     *
     *
     * */
    @Override
    public List<ScriptErrorDto> execute(ScriptId scriptId) {
        List<ScriptError> scriptErrors = scriptErrorRepositoryGateWay.find(scriptId);
        return scriptErrors.stream().map(ScriptErrorDto::new).collect(Collectors.toList());
    }

}

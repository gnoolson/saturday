package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.inbound.GetScriptUseCase;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class GetScriptUseCaseImpl implements GetScriptUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;

    @Override
    public ScriptDto execute(ScriptId scriptId) {
        Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));

        return new ScriptDto(
                script.getId(),
                script.getProjectId(),
                script.getName(),
                script.getDescription(),
                script.getCode(),
                script.isEnabled(),
                script.isBlocked(),
                script.getIncludedScripts(),
                script.getCachingTime(),
                script.isAutostart(),
                script.getErrorEventHandler()
        );
    }

}

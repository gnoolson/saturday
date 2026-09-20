package gnoolson.saturday.script.application;

import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.port.inbound.GetAllScriptsUseCase;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class GetAllScriptsUseCaseImpl implements GetAllScriptsUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final ScriptErrorRepositoryGateway scriptErrorRepositoryGateWay;

    @Override
    public List<ScriptDto> execute() {
        List<Script> scripts = scriptRepositoryGateway.findAll();
        List<ScriptDto> result = new ArrayList<>(scripts.size());

        scripts.forEach((script) -> {
            ScriptDto scriptDto = new ScriptDto(
                    script.getId(),
                    script.getProjectId(),
                    script.getName(),
                    script.getDescription(),
                    script.isEnabled(),
                    script.isBlocked(),
                    scriptErrorRepositoryGateWay.count(script.getId()),
                    script.isAutostart()
            );
            result.add(scriptDto);
        });

        return result;
    }

}

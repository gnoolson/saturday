package gnoolson.saturday.script.application;

import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.port.inbound.AutostartScriptsUseCase;
import gnoolson.saturday.script.port.outbound.AutostartScriptLauncherGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AutostartScriptsUseCaseImpl implements AutostartScriptsUseCase {

    private final ScriptRepositoryGateway scriptRepositoryGateway;
    private final AutostartScriptLauncherGateway autostartScriptLauncherGateway;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        List<Script> scripts = scriptRepositoryGateway.findAllAutostartScripts();

        for (Script script : scripts) {
            autostartScriptLauncherGateway.execute(script.getId());
        }
    }

}

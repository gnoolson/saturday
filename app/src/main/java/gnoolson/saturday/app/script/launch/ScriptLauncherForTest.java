package gnoolson.saturday.app.script.launch;

import gnoolson.saturday.app.script.ScriptData;
import gnoolson.saturday.app.script.ScriptDataProvider;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class ScriptLauncherForTest {

    private final ScriptExecutor scriptExecutor;
    private final ScriptRepositoryGateway scriptRepositoryGateway;

    /*
     *
     *
     * */
    public long execute(Collection<Functionality> functionalities, ProjectId projectId, ScriptId scriptId, Code code, Collection<ScriptId> includedScriptIds) {
        return scriptExecutor.execute(functionalities, scriptId, new ScriptDataProviderImpl(projectId, code, includedScriptIds));
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    private class ScriptDataProviderImpl implements ScriptDataProvider {

        private final ProjectId projectId;
        private final Code code;
        private final Collection<ScriptId> includedScriptIds;

        @Override
        public ScriptData execute(ScriptId scriptId) {
            List<ScriptData.Script> includedScripts = new ArrayList<>(includedScriptIds.size());
            for (ScriptId includedScriptId : includedScriptIds) {
                Optional<Script> scriptOpt = scriptRepositoryGateway.find(projectId, includedScriptId);
                if (!scriptOpt.isPresent())
                    continue;

                Script includedScript = scriptOpt.get();
                if (!includedScript.isEnabled())
                    continue;

                includedScripts.add(
                        new ScriptData.Script(includedScript.getName(), includedScript.getCode())
                );
            }

            return new ScriptData(
                    new ScriptData.Script(ScriptName.of("Dev"), code),
                    includedScripts,
                    CachingTime.notCacheable()
            );
        }
    }


}

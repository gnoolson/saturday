package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.vo.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface ImportScriptsUseCase {

    void execute(List<ScriptDto> scripts);

    @Getter
    @RequiredArgsConstructor
    class ScriptDto {
        private final ScriptId id;
        private final ProjectId projectId;
        private final ScriptName name;
        private final Description description;
        private final Code code;
        private final List<ScriptId> includedScripts;
        private final CachingTime cachingTime;
        private final boolean autostart;
        private final ScriptId errorEventHandler;
    }

}

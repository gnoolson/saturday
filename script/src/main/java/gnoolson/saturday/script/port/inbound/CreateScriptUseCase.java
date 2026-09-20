package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.vo.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface CreateScriptUseCase {

    ScriptId execute(ScriptDto scriptDto);

    @RequiredArgsConstructor
    @Getter
    class ScriptDto {
        private final ProjectId projectId;
        private final ScriptName name;
        private final Description description;
        private final CachingTime cachingTime;
        private final boolean autostart;
        private final ScriptId errorEventHandler;
        private final Code code;
    }

}

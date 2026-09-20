package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface UpdateScriptUseCase {

    void execute(ScriptDto scriptDto);

    @RequiredArgsConstructor
    @Getter
    class ScriptDto {
        private final ScriptId id;
        private final ProjectId projectId;
        private final ScriptName name;
        private final Description description;
        private final CachingTime cachingTime;
        private final boolean autostart;
        private final ScriptId errorEventHandler;
    }

}

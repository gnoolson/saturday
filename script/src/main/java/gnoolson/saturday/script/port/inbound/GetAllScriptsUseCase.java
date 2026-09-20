package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface GetAllScriptsUseCase {

    List<ScriptDto> execute();

    @Getter
    @RequiredArgsConstructor
    class ScriptDto {
        private final ScriptId id;
        private final ProjectId projectId;
        private final ScriptName name;
        private final Description description;
        private final boolean enabled;
        private final boolean blocked;
        private final PositiveNumber errors;
        private final boolean autostart;
    }

}

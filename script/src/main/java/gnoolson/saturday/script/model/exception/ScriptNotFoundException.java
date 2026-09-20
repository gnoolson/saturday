package gnoolson.saturday.script.model.exception;

import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScriptNotFoundException extends RuntimeException {

    private final ScriptId scriptId;

    @Override
    public String getMessage() {
        return String.format("Script \"%s\" was not found", scriptId.getValue().toString());
    }

}

package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;

import java.util.Map;

public interface ScriptErrorHandler {

    void execute(ScriptId scriptId, Exception exception, ScriptLauncher scriptLauncher);

    interface ScriptLauncher {
        void execute(ScriptId scriptId, Map<String, Object> args);
    }

}

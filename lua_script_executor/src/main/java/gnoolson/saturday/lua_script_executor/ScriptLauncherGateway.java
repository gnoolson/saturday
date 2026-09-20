package gnoolson.saturday.lua_script_executor;

import java.util.Map;

public interface ScriptLauncherGateway {

    void execute(String scriptId, String luaLibId, Map<String, Object> args);

}

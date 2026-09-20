package gnoolson.saturday.lua_script_executor.lib;

import org.luaj.vm2.Globals;

public interface LuaLib {

    String name();

    String getId();

    void updateFunctionality(Object functionality);

    void release();

    default void setGlobals(Globals globals) {
    }

}

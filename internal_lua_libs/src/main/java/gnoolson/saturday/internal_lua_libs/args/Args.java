package gnoolson.saturday.internal_lua_libs.args;

import gnoolson.saturday.lua_script_executor.lib.Functionality;

import java.util.Map;

public interface Args extends Functionality {

    void setup(Map<String, Object> data);

    Map<String, Object> get();

}

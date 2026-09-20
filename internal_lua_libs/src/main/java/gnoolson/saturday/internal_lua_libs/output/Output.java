package gnoolson.saturday.internal_lua_libs.output;

import gnoolson.saturday.lua_script_executor.lib.Functionality;

public interface Output extends Functionality {

    void print(String... msgs);

}

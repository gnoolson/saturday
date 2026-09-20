package gnoolson.saturday.lua_script_executor.lib;

import gnoolson.saturday.lua_script_executor.Output;

public interface Functionality {

    String getLuaLibId();

    void writeResultToOutput(Output output);

    Object getFunctionalityInstance();

}
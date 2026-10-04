package gnoolson.saturday.internal_lua_libs.log;

import gnoolson.saturday.lua_script_executor.lib.Functionality;

public interface Log extends Functionality {

    void info(String... msgs);

    void warn(String... msgs);

    void error(String... msgs);

    void debug(String... msgs);

    void useOutput(boolean flag);

    void setup(LogGateway logGateway);

    void setup(LogGateway logGateway, boolean debug);

    boolean isDebugEnabled();

}

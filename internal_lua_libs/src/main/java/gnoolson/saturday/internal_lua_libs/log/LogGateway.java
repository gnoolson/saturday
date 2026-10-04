package gnoolson.saturday.internal_lua_libs.log;

public interface LogGateway {

    boolean isDebugEnabled();

    void setDebugEnabled(boolean flag);

    void close();

    boolean clear();

    void info(String... msgs);

    void warn(String... msgs);

    void error(String... msgs);

    void debug(String... msgs);

}

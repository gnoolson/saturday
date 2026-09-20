package gnoolson.saturday.lua_script_executor.utils;

import org.luaj.vm2.LuaError;

public class LuaErrorUtil {

    public static String getMessage(Throwable t) {
        return getLuaError(t).getMessage();
    }

    public static RuntimeException getLuaError(Throwable t) {
        if (t instanceof LuaError)
            return (LuaError) t;

        Throwable cause = t.getCause();
        if (cause != null) {
            return getLuaError(cause);
        }

        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else
            return new RuntimeException(t);
    }

}

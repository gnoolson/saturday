package gnoolson.saturday.lua_script_executor.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaError;

class LuaErrorUtilTest {

    @Test
    public void general() {
        {
            LuaError luaError = new LuaError("ERROR");
            String luaErrorMessage = LuaErrorUtil.getMessage(luaError);
            Assertions.assertEquals("ERROR", luaErrorMessage);
        }

        {
            LuaError luaError = new LuaError("ERROR");
            RuntimeException runtimeException = new RuntimeException(luaError);
            String luaErrorMessage = LuaErrorUtil.getMessage(runtimeException);
            Assertions.assertEquals("ERROR", luaErrorMessage);
        }

        {
            RuntimeException runtimeException = new RuntimeException("RUNTIME_EXCEPTION");
            String luaErrorMessage = LuaErrorUtil.getMessage(runtimeException);
            Assertions.assertEquals("RUNTIME_EXCEPTION", luaErrorMessage);
        }

        {
            String luaErrorMessage = LuaErrorUtil.getMessage(new RuntimeException(new RuntimeException(new RuntimeException(new RuntimeException("RUNTIME_EXCEPTION")))));
            Assertions.assertEquals("RUNTIME_EXCEPTION", luaErrorMessage);
        }

        {
            String luaErrorMessage = LuaErrorUtil.getMessage(new RuntimeException(new RuntimeException(new RuntimeException(new RuntimeException(new LuaError("ERROR"))))));
            Assertions.assertEquals("ERROR", luaErrorMessage);
        }

    }

}
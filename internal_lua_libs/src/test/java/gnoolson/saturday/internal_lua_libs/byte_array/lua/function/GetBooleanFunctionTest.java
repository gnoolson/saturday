package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaValue;


class GetBooleanFunctionTest {

    @Test
    public void general() {
        GetBooleanFunction function = new GetBooleanFunction();

        {
            byte[] data = {(byte) 0x5};
            LuaBoolean result = (LuaBoolean) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));
            Assertions.assertEquals(LuaValue.TRUE, result);
        }

        {
            byte[] data = {(byte) 0x0};
            LuaBoolean result = (LuaBoolean) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));
            Assertions.assertEquals(LuaValue.FALSE, result);
        }

    }

    @Test
    public void position() {
        GetBooleanFunction function = new GetBooleanFunction();

        {
            byte[] data = {0x0, 0x0, (byte) 0x5, 0x0, 0x0, 0x0, 0x0, 0x0,};
            LuaBoolean result = (LuaBoolean) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));
            Assertions.assertEquals(LuaValue.TRUE, result);
        }

        {
            byte[] data = {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,};
            LuaBoolean result = (LuaBoolean) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));
            Assertions.assertEquals(LuaValue.FALSE, result);
        }
    }

}
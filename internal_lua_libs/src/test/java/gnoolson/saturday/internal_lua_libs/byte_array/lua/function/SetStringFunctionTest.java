package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

class SetStringFunctionTest {

    @Test
    public void general() {
        SetStringFunction function = new SetStringFunction();
        String str = "hello world";

        LuaUserdata luaUserdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[11]), LuaString.valueOf(str), LuaValue.valueOf(0));

        byte[] result = (byte[]) luaUserdata.checkuserdata(byte[].class);

        byte[] expected = {0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64};

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void general_with_position() {
        SetStringFunction function = new SetStringFunction();
        String str = "hello world";

        LuaUserdata luaUserdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[16]), LuaString.valueOf(str), LuaValue.valueOf(5));

        byte[] result = (byte[]) luaUserdata.checkuserdata(byte[].class);

        byte[] expected = {0x0, 0x0, 0x0, 0x0, 0x0, 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64};

        Assertions.assertArrayEquals(expected, result);
    }

}
package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetInt64FunctionTest {

    @Test
    public void general_be() {
        SetInt64Function function = new SetInt64Function(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[8]), LuaValue.valueOf(9.22337203685477581e+18), LuaValue.valueOf(0));

        byte[] expected = {0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetInt64Function function = new SetInt64Function(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[8]), LuaValue.valueOf(9.22337203685477581e+18), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
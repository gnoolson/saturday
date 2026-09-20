package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetInt32FunctionTest {

    @Test
    public void general_be() {
        SetInt32Function function = new SetInt32Function(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(-2000000000), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0x88, (byte) 0xCA, 0x6C, 0x00};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetInt32Function function = new SetInt32Function(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(-2000000000), LuaValue.valueOf(0));

        byte[] expected = {0x00, 0x6C, (byte) 0xCA, (byte) 0x88};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
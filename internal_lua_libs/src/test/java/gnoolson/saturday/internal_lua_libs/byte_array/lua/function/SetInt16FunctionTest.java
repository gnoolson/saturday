package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetInt16FunctionTest {

    @Test
    public void general_be() {
        SetInt16Function function = new SetInt16Function(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[2]), LuaValue.valueOf(-22222), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xA9, 0x32};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetInt16Function function = new SetInt16Function(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[2]), LuaValue.valueOf(-22222), LuaValue.valueOf(0));

        byte[] expected = {0x32, (byte) 0xA9};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
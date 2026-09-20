package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetUint16FunctionTest {

    @Test
    public void general_be() {
        SetUint16Function function = new SetUint16Function(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[2]), LuaValue.valueOf(65000), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xFD, (byte) 0xE8};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetUint16Function function = new SetUint16Function(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[2]), LuaValue.valueOf(65000), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xE8, (byte) 0xFD};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
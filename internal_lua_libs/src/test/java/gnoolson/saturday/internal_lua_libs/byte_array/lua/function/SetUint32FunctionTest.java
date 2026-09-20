package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetUint32FunctionTest {

    @Test
    public void general_be() {
        SetUint32Function function = new SetUint32Function(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(4_000_000_000L), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xEE, (byte) 0x6B, (byte) 0x28, 0x00};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetUint32Function function = new SetUint32Function(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(4_000_000_000L), LuaValue.valueOf(0));

        byte[] expected = {0x00, (byte) 0x28, (byte) 0x6B, (byte) 0xEE};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
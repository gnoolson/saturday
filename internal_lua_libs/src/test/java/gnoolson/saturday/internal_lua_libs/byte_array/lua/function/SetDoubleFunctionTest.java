package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetDoubleFunctionTest {

    @Test
    public void general_be() {
        SetDoubleFunction function = new SetDoubleFunction(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[8]), LuaValue.valueOf(3.14156), LuaValue.valueOf(0));

        byte[] expected = {0x40, 0x09, 0x21, (byte) 0xea, 0x35, (byte) 0x93, 0x5f, (byte) 0xc4};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetDoubleFunction function = new SetDoubleFunction(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[8]), LuaValue.valueOf(3.14156), LuaValue.valueOf(0));

        byte[] expected = {(byte) 0xc4, 0x5f, (byte) 0x93, 0x35, (byte) 0xea, 0x21, 0x09, 0x40};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
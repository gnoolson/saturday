package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

class SetFloatFunctionTest {

    @Test
    public void general_be() {
        SetFloatFunction function = new SetFloatFunction(ByteOrder.BIG_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(3.14156), LuaValue.valueOf(0));

        byte[] expected = {0x40, 0x49, 0x0f, 0x52};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }

    @Test
    public void general_le() {
        SetFloatFunction function = new SetFloatFunction(ByteOrder.LITTLE_ENDIAN);

        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[4]), LuaValue.valueOf(3.14156), LuaValue.valueOf(0));

        byte[] expected = {0x52, 0x0f, 0x49, 0x40};
        Assertions.assertArrayEquals(expected, (byte[]) userdata.checkuserdata(byte[].class));
    }
}
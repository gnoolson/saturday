package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;


class GetInt32FunctionTest {

    @Test
    public void general_be() {
        GetInt32Function function = new GetInt32Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {(byte) 0x88, (byte) 0xCA, 0x6C, 0x00};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(-2000000000), result);
    }

    @Test
    public void general_le() {
        GetInt32Function function = new GetInt32Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0x00, 0x6C, (byte) 0xCA, (byte) 0x88};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(-2000000000), result);
    }

    @Test
    public void position_be() {
        GetInt32Function function = new GetInt32Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0x88, (byte) 0xCA, 0x6C, 0x00, 0xF, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(-2000000000), result);
    }

    @Test
    public void position_le() {
        GetInt32Function function = new GetInt32Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0x00, 0xF, 0x00, 0x6C, (byte) 0xCA, (byte) 0x88, 0x00, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(-2000000000), result);
    }

}
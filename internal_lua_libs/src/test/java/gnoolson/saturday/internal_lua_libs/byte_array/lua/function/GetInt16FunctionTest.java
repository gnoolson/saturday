package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;


class GetInt16FunctionTest {

    @Test
    public void general_be() {
        GetInt16Function function = new GetInt16Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {(byte) 0xA9, 0x32};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(-22222), result);
    }

    @Test
    public void general_le() {
        GetInt16Function function = new GetInt16Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0x32, (byte) 0xA9};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(-22222), result);
    }

    @Test
    public void position_be() {
        GetInt16Function function = new GetInt16Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0xA9, 0x32, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(-22222), result);
    }

    @Test
    public void position_le() {
        GetInt16Function function = new GetInt16Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF, 0x32, (byte) 0xA9, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(-22222), result);
    }

}
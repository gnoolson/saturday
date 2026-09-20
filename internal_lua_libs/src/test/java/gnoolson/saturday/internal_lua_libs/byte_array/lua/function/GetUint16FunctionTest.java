package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;


class GetUint16FunctionTest {

    @Test
    public void general_be() {
        GetUint16Function function = new GetUint16Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {(byte) 0xFD, (byte) 0xE8};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(65000), result);
    }

    @Test
    public void general_le() {
        GetUint16Function function = new GetUint16Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {(byte) 0xE8, (byte) 0xFD};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(65000), result);
    }

    @Test
    public void position_be() {
        GetUint16Function function = new GetUint16Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0xFD, (byte) 0xE8, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(65000), result);
    }

    @Test
    public void position_le() {
        GetUint16Function function = new GetUint16Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0xE8, (byte) 0xFD, 0xF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(65000), result);
    }

}
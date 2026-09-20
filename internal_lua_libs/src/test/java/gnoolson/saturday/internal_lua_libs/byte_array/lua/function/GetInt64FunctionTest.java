package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;


class GetInt64FunctionTest {

    @Test
    public void general_be() {
        GetInt64Function function = new GetInt64Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(9.22337203685477581e+18), result);
    }

    @Test
    public void general_le() {
        GetInt64Function function = new GetInt64Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(9.22337203685477581e+18), result);
    }

    @Test
    public void position_be() {
        GetInt64Function function = new GetInt64Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0x0, 0x0, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0, 0x0};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(9.22337203685477581e+18), result);
    }

    @Test
    public void position_le() {
        GetInt64Function function = new GetInt64Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(9.22337203685477581e+18), result);
    }

}
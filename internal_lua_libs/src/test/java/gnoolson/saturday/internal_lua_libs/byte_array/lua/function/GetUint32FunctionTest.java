package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;


class GetUint32FunctionTest {

    @Test
    public void general_be() {
        GetUint32Function function = new GetUint32Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {(byte) 0xEE, (byte) 0x6B, (byte) 0x28, 0x00};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(4_000_000_000L), result);
    }

    @Test
    public void general_le() {
        GetUint32Function function = new GetUint32Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0x00, (byte) 0x28, (byte) 0x6B, (byte) 0xEE};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(4000000000L), result);
    }

    @Test
    public void position_be() {
        GetUint32Function function = new GetUint32Function(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF, (byte) 0xEE, (byte) 0x6B, (byte) 0x28, 0x00, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(4000000000L), result);
    }

    @Test
    public void position_le() {
        GetUint32Function function = new GetUint32Function(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF, 0x00, (byte) 0x28, (byte) 0x6B, (byte) 0xEE, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(4000000000L), result);
    }

}
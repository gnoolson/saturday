package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

class GetInt8FunctionTest {

    @Test
    public void general() {
        GetInt8Function function = new GetInt8Function();

        byte[] data = {(byte) 0x80};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(-128), result);
    }

    @Test
    public void position() {
        GetInt8Function function = new GetInt8Function();

        byte[] data = {0x0, 0x0, (byte) 0x80, 0x0, 0x0, 0x0, 0x0, 0x0,};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertEquals(LuaValue.valueOf(-128), result);
    }

}
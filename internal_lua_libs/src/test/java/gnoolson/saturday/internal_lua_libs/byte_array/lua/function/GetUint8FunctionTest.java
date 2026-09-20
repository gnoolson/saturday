package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

class GetUint8FunctionTest {

    @Test
    public void general() {
        GetUint8Function function = new GetUint8Function();

        byte[] data = {(byte) 0xFF};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(LuaValue.valueOf(255), result);
    }

    @Test
    public void position() {
        GetUint8Function function = new GetUint8Function();

        byte[] data = {0xF, 0xF, 0xf, 0xF, 0xF, (byte) 0xFF, 0xF,};
        LuaInteger result = (LuaInteger) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(5));

        Assertions.assertEquals(LuaValue.valueOf(255), result);
    }

}
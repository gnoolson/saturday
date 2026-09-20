package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;


class GetInt8SequenceFunctionTest {

    @Test
    public void general() {
        GetInt8SequenceFunction getInt8SequenceFunction = new GetInt8SequenceFunction();

        byte[] data = {0x09, 0x05, 0x03, 0x10, 0x20, 0x30};

        LuaValue result = getInt8SequenceFunction.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertEquals(data.length, result.length());

        for (int i = 0; i < result.length(); i++) {
            LuaValue luaValue = result.get(i + 1);
            Assertions.assertEquals(data[i], luaValue.tobyte());
        }
    }

    @Test
    public void position() {
        GetInt8SequenceFunction getInt8SequenceFunction = new GetInt8SequenceFunction();

        byte[] data = {0x09, 0x05, 0x03, 0x10, 0x7, 0x2};

        LuaValue result = getInt8SequenceFunction.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        byte[] expected = {0x03, 0x10, 0x7, 0x2};

        Assertions.assertEquals(expected.length, result.length());

        for (int i = 0; i < result.length(); i++) {
            LuaValue luaValue = result.get(i + 1);
            Assertions.assertEquals(expected[i], luaValue.tobyte());
        }
    }

}
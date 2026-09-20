package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

class MergeFunctionTest {
    @Test
    public void general() {
        MergeFunction function = new MergeFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        ByteArrayLuaTable result = (ByteArrayLuaTable) function.call(
                self,
                new ByteArrayLuaTable(new byte[]{(byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2}),
                LuaValue.valueOf(0));

        Assertions.assertArrayEquals(
                new byte[]{(byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2, 0x05, 0x06, 0x07, 0x08},
                result.getBytes()
        );
    }

}
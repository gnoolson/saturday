package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

class PutFunctionTest {

    @Test
    public void general() {
        PutFunction function = new PutFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        function.call(
                self,
                new ByteArrayLuaTable(new byte[]{(byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2}),
                LuaValue.valueOf(0));

        Assertions.assertArrayEquals(
                new byte[]{(byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2, 0x05, 0x06, 0x07, 0x08},
                self.getBytes()
        );
    }

    @Test
    public void general_with_position() {
        PutFunction function = new PutFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        function.call(
                self,
                new ByteArrayLuaTable(new byte[]{(byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2}),
                LuaValue.valueOf(2));

        Assertions.assertArrayEquals(
                new byte[]{0x01, 0x02, (byte) 0xA0, (byte) 0xA1, (byte) 0xA2, (byte) 0xA2, 0x07, 0x08},
                self.getBytes()
        );
    }

}
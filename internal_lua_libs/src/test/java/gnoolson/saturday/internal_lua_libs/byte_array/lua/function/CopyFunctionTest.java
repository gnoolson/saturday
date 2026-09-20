package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

class CopyFunctionTest {

    @Test
    public void general() {

        CopyFunction function = new CopyFunction();
        ByteArrayLuaTable byteArrayLuaTable = (ByteArrayLuaTable) function.call(
                new ByteArrayLuaTable(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09}),
                LuaValue.valueOf(2), LuaValue.valueOf(2));

        byte[] bytes = byteArrayLuaTable.getBytes();

        Assertions.assertArrayEquals(new byte[]{
                0x02, 0x03
        }, bytes);
    }

}
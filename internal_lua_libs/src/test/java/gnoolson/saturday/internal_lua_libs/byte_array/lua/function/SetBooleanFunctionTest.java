package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

class SetBooleanFunctionTest {

    @Test
    public void general() {
        SetBooleanFunction function = new SetBooleanFunction();

        {
            ByteArrayLuaTable byteArrayLuaTable = new ByteArrayLuaTable(new byte[1]);
            function.call(byteArrayLuaTable, LuaValue.valueOf(true), LuaValue.valueOf(0));
            Assertions.assertArrayEquals(new byte[]{(byte) 0x1}, byteArrayLuaTable.getBytes());
        }

        {
            ByteArrayLuaTable byteArrayLuaTable = new ByteArrayLuaTable(new byte[1]);
            function.call(byteArrayLuaTable, LuaValue.valueOf(false), LuaValue.valueOf(0));
            Assertions.assertArrayEquals(new byte[]{0}, byteArrayLuaTable.getBytes());
        }
    }

}
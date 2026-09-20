package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

class LengthFunctionTest {

    @Test
    public void general() {
        LengthFunction function = new LengthFunction();

        LuaInteger length = (LuaInteger) function.call(new ByteArrayLuaTable(new byte[]{0x0F, 0x0F, 0x0F, 0x0F}));

        Assertions.assertEquals(LuaValue.valueOf(4), length);
    }

}
package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

class GetStringFunctionTest {

    @Test
    public void general_without_zero() {
        GetStringFunction function = new GetStringFunction(false);

        byte[] data = {0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21};
        LuaString result = (LuaString) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0), LuaValue.valueOf(data.length));

        Assertions.assertEquals(LuaValue.valueOf("hello world !"), result);
    }

    @Test
    public void general_with_zero() {
        GetStringFunction function = new GetStringFunction(false);

        byte[] data = {0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21, 0x0};
        LuaString result = (LuaString) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0), LuaValue.valueOf(data.length - 1));

        Assertions.assertEquals(LuaValue.valueOf("hello world !"), result);
    }

    @Test
    public void general_safe() {
        GetStringFunction function = new GetStringFunction(true);

        byte[] data = {0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21, 0x0, 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21};
        LuaString result = (LuaString) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0), LuaValue.valueOf(data.length));

        Assertions.assertEquals(LuaValue.valueOf("hello world !"), result);
    }

    @Test
    public void general_with_position_and_length() {
        GetStringFunction function = new GetStringFunction(false);

        byte[] data = {0xF, 0xF, 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21, 0x0, 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x20, 0x21};
        LuaString result = (LuaString) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2), LuaValue.valueOf(13));

        Assertions.assertEquals(LuaValue.valueOf("hello world !"), result);
    }

}
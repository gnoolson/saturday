package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

class SetInt8FunctionTest {

    @Test
    public void general() {
        SetInt8Function function = new SetInt8Function();
        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[1]), LuaValue.valueOf(-128), LuaValue.valueOf(0));
        Assertions.assertArrayEquals(new byte[]{(byte) 0x80}, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
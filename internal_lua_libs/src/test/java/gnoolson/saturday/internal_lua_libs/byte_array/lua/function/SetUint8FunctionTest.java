package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

class SetUint8FunctionTest {

    @Test
    public void general() {
        SetUint8Function function = new SetUint8Function();
        LuaUserdata userdata = (LuaUserdata) function.call(new ByteArrayLuaTable(new byte[1]), LuaValue.valueOf(254), LuaValue.valueOf(0));
        Assertions.assertArrayEquals(new byte[]{(byte) 0xFE}, (byte[]) userdata.checkuserdata(byte[].class));
    }

}
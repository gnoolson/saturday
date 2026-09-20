package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.nio.charset.StandardCharsets;

public class FromStringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue stringLuaValue) {
        String string = LuaArgUtils.getStringFromFunctionArgs(stringLuaValue, 1, "string");
        return new ByteArrayLuaTable(string.getBytes(StandardCharsets.UTF_8));
    }

}

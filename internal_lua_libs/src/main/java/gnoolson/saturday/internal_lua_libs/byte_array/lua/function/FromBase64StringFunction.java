package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.Base64;

public class FromBase64StringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue hexStringLuaValue) {
        String base64String = LuaArgUtils.getStringFromFunctionArgs(hexStringLuaValue, 1, "base64String");
        byte[] data = Base64.getDecoder().decode(base64String);
        return new ByteArrayLuaTable(data);
    }

}

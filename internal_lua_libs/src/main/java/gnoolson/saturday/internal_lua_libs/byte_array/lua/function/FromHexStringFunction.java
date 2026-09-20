package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.common.HexFormat;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class FromHexStringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue hexStringLuaValue) {
        String hexString = LuaArgUtils.getStringFromFunctionArgs(hexStringLuaValue, 1, "hexString");
        return new ByteArrayLuaTable(HexFormat.stringToByteArray(hexString));
    }

}

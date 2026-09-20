package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class AllocateFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue lengthLuaValue) {
        int length = LuaArgUtils.getIntFromFunctionArgs(lengthLuaValue, 1, "length");
        return new ByteArrayLuaTable(new byte[length]);
    }

}

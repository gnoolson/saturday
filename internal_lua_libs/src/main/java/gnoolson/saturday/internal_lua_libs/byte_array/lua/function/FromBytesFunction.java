package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;


public class FromBytesFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue userDataLuaValue) {
        byte[] bytes = LuaArgUtils.getByteArrayFromFunctionArgs(userDataLuaValue, 1, "bytes");
        return new ByteArrayLuaTable(bytes);
    }

}

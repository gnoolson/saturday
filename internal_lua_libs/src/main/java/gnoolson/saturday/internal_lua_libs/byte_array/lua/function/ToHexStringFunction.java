package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.common.HexFormat;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class ToHexStringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue) {
        ByteArrayLuaTable self = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        return LuaValue.valueOf(HexFormat.bytesToString(self.getBytes()));
    }

}

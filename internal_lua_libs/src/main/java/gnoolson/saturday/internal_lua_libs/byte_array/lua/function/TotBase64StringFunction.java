package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import java.util.Base64;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class TotBase64StringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue) {
        ByteArrayLuaTable self = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        String base64String = Base64.getEncoder().encodeToString(self.getBytes());
        return LuaValue.valueOf(base64String);
    }

}

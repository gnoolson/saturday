package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

@RequiredArgsConstructor
public class LengthFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        return LuaValue.valueOf(byteArrayLuaTable.getBytes().length);
    }

}

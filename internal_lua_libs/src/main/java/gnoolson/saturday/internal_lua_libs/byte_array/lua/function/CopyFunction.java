package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class CopyFunction extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue positionLuaValue, LuaValue lengthLuaValue) {
        ByteArrayLuaTable self = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");
        int length = LuaArgUtils.getIntFromFunctionArgs(lengthLuaValue, 3, "length");

        byte[] bytes = new byte[length];
        System.arraycopy(self.getBytes(), position, bytes, 0, length);

        return new ByteArrayLuaTable(bytes);
    }

}

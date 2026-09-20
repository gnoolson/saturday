package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import java.nio.charset.StandardCharsets;

public class SetStringFunction extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue stringLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);

        String string = LuaArgUtils.getStringFromFunctionArgs(stringLuaValue, 2, "string");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");
        int length = string.length();
        byte[] bytes = byteArrayLuaTable.getBytes();

        byte[] stringBytes = string.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(stringBytes, 0, bytes, position, length);

        return new LuaUserdata(bytes);
    }

}

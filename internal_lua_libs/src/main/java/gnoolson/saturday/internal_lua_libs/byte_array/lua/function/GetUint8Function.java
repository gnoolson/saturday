package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.nio.ByteBuffer;

public class GetUint8Function extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");

        int i = ByteBuffer.wrap(byteArrayLuaTable.getBytes()).get(position) & 0xFF;
        return LuaString.valueOf(i);
    }

}

package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import java.nio.ByteBuffer;

public class SetBooleanFunction extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue booleanLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        boolean flag = LuaArgUtils.getBooleanFromFunctionArgs(booleanLuaValue, 2, "flag");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayLuaTable.getBytes());
        byteBuffer.position(position);
        byteBuffer.put(flag? (byte)1: 0);

        return LuaValue.ONE;
    }

}


package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class GetUint8SequenceFunction extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");

        byte[] byteArray = byteArrayLuaTable.getBytes();
        int[] result = new int[byteArray.length - position];

        for (int i = 0, j = position; j < byteArray.length; i++, j++) {
            int unsignedByte = byteArray[j] & 0xFF;
            result[i] = unsignedByte;
        }

        LuaTable luaTable = new LuaTable();
        for (int i = 0; i < result.length; i++) {
            luaTable.set(i + 1, LuaInteger.valueOf(result[i]));
        }

        return luaTable;
    }

}

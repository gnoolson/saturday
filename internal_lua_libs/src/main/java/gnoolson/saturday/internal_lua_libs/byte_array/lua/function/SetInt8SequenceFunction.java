package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class SetInt8SequenceFunction extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue sequenceLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        LuaTable sequence = LuaArgUtils.getLuaTableFromFunctionArgs(sequenceLuaValue, 2, "sequence");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");

        byte[] data = byteArrayLuaTable.getBytes();

        int n = sequence.length();
        for (int i = 0; i < n; i++) {
            int value = sequence.get(i + 1).toint();

            if (value < -128 || value > 127)
                throw new IllegalArgumentException("Value out of \"int8\" range: " + value); // +

            data[i + position] = (byte) value;
        }

        return LuaValue.NIL;
    }

}


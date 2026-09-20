package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class MergeFunction extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue byteArrayLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable self = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        ByteArrayLuaTable source = (ByteArrayLuaTable) LuaArgUtils.getLuaTableFromFunctionArgs(byteArrayLuaValue, 2, "byteArray");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");

        byte[] bytes = new byte[self.getBytes().length];
        System.arraycopy(self.getBytes(), 0, bytes, 0, self.getBytes().length);

        byte[] sourceBytes = source.getBytes();

        for (int i = 0, j = position; i < sourceBytes.length; i++, j++) {
            bytes[j] = sourceBytes[i];
        }

        return new ByteArrayLuaTable(bytes);
    }

}

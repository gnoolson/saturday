package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class ToBinaryStringFunction extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue) {
        ByteArrayLuaTable self = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < self.getBytes().length; i++) {
            int unsigned = self.getBytes()[i] & 0xFF;
            String binary = String.format("%8s", Integer.toBinaryString(unsigned)).replace(' ', '0');
            sb.append(binary);

            if (i < self.getBytes().length - 1)
                sb.append(" ");
        }

        return LuaValue.valueOf(sb.toString());
    }

}

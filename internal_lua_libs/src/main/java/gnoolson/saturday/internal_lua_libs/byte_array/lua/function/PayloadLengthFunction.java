package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

@RequiredArgsConstructor
public class PayloadLengthFunction extends OneArgFunction {

    // +
    @Override
    public LuaValue call(LuaValue selfLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);

        byte[] bytes = byteArrayLuaTable.getBytes();

        int result = 0;
        for (int i = bytes.length; i > 0; i--) {
            byte data = bytes[i - 1];
            if (data == 0)
                continue;

            result = i;
            break;
        }

        return LuaValue.valueOf(result);
    }

}

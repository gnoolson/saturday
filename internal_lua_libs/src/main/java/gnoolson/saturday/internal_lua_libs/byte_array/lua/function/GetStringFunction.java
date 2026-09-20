package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RequiredArgsConstructor
public class GetStringFunction extends ThreeArgFunction {

    private final boolean safe;

    /*
     *
     *
     * */
    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue positionLuaValue, LuaValue lengthLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        byte[] byteArray = byteArrayLuaTable.getBytes();

        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");

        int length = byteArray.length;
        if (!lengthLuaValue.equals(LuaValue.NIL)) {
            length = LuaArgUtils.getIntFromFunctionArgs(lengthLuaValue, 3, "length");
            length += position;
        }

        if (safe) {
            for (int i = 0; i < length; i++) {
                if (byteArray[i] == 0) {
                    length = i;
                    break;
                }
            }
        }

        byte[] bytes = Arrays.copyOfRange(
                byteArray,
                position,
                length
        );

        return LuaString.valueOf(new String(bytes, StandardCharsets.UTF_8));
    }

}

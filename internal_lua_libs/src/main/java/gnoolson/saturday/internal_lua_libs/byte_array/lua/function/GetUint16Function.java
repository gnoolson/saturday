package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@RequiredArgsConstructor
public class GetUint16Function extends TwoArgFunction {

    private final ByteOrder byteOrder;

    /*
     *
     *
     * */
    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");

        int i = ByteBuffer.wrap(byteArrayLuaTable.getBytes()).order(byteOrder).getShort(position) & 0xFFFF;
        return LuaString.valueOf(i);
    }

}

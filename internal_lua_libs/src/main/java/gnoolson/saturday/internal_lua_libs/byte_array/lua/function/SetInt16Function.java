package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@RequiredArgsConstructor
public class SetInt16Function extends ThreeArgFunction {

    private final ByteOrder byteOrder;

    /*
     *
     *
     * */
    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue numberLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int number = LuaArgUtils.getIntFromFunctionArgs(numberLuaValue, 2, "number");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");

        if (number < -32768 || number > 32767)
            throw new IllegalArgumentException("Value out of \"int16\" range: " + number); // +

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayLuaTable.getBytes());
        byteBuffer.position(position);
        byteBuffer.order(byteOrder);

        return new LuaUserdata(byteBuffer.putShort((short) number).array());
    }

}

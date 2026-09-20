package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import java.nio.ByteBuffer;

public class SetUint8Function extends ThreeArgFunction {

    @Override
    public LuaValue call(LuaValue selfLuaValue, LuaValue numberLuaValue, LuaValue positionLuaValue) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(selfLuaValue);
        int number = LuaArgUtils.getIntFromFunctionArgs(numberLuaValue, 2, "number");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 3, "position");

        if (number < 0 || number > 255)
            throw new IllegalArgumentException("Value out of \"uint8\" range: " + number); // +

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayLuaTable.getBytes());
        byteBuffer.position(position);

        return new LuaUserdata(byteBuffer.put((byte) number).array());
    }

}


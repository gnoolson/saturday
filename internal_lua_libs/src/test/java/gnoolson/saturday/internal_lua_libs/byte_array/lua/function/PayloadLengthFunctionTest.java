package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

class PayloadLengthFunctionTest {

    @Test
    public void general() {
        {
            byte[] data = new byte[]{0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11};
            ByteArrayLuaTable byteArray = new ByteArrayLuaTable(data);
            PayloadLengthFunction payloadLengthFunction = new PayloadLengthFunction();
            LuaValue result = payloadLengthFunction.call(byteArray);

            Assertions.assertEquals(data.length, result.checkint());
        }
        {
            byte[] data = new byte[]{
                    0x11, 0x11, 0x11, 0x11, 0x11,
                    0x00, 0x00, 0x00, 0x00, 0x00
            };
            ByteArrayLuaTable byteArray = new ByteArrayLuaTable(data);
            PayloadLengthFunction payloadLengthFunction = new PayloadLengthFunction();
            LuaValue result = payloadLengthFunction.call(byteArray);

            Assertions.assertEquals(data.length / 2, result.checkint());
        }
    }

}
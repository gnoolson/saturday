package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

// 32 bit
class GetFloatFunctionTest {

    double epsilon = 0.00001;


    @Test
    public void general_be() {
        GetFloatFunction function = new GetFloatFunction(ByteOrder.BIG_ENDIAN);

        byte[] data = {0x40, 0x49, 0x0f, 0x52, 0x0, 0x0, 0x0, 0x0, 0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

    @Test
    public void general_le() {
        GetFloatFunction function = new GetFloatFunction(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0x52, 0x0f, 0x49, 0x40, 0x0, 0x0, 0x0, 0x0, 0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

    @Test
    public void position_be() {
        GetFloatFunction function = new GetFloatFunction(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF,
                0x40, 0x49, 0x0f, 0x52, 0x0, 0x0, 0x0, 0x0,
                0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);

    }

    @Test
    public void position_le() {
        GetFloatFunction function = new GetFloatFunction(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF,
                0x52, 0x0f, 0x49, 0x40, 0x0, 0x0, 0x0, 0x0,
                0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

}
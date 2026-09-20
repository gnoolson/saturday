package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaValue;

import java.nio.ByteOrder;

// 64bit
class GetDoubleFunctionTest {

    double epsilon = 0.00001;

    @Test
    public void general_be() {
        GetDoubleFunction function = new GetDoubleFunction(ByteOrder.BIG_ENDIAN);

        byte[] data = {0x40, 0x09, 0x21, (byte) 0xea, 0x35, (byte) 0x93, 0x5f, (byte) 0xc4};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

    @Test
    public void general_le() {
        GetDoubleFunction function = new GetDoubleFunction(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {(byte) 0xc4, 0x5f, (byte) 0x93, 0x35, (byte) 0xea, 0x21, 0x09, 0x40};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(0));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

    @Test
    public void position_be() {
        GetDoubleFunction function = new GetDoubleFunction(ByteOrder.BIG_ENDIAN);

        byte[] data = {0xF, 0xF,
                0x40, 0x09, 0x21, (byte) 0xea, 0x35, (byte) 0x93, 0x5f, (byte) 0xc4,
                0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);

    }

    @Test
    public void position_le() {
        GetDoubleFunction function = new GetDoubleFunction(ByteOrder.LITTLE_ENDIAN);

        byte[] data = {0xF, 0xF,
                (byte) 0xc4, 0x5f, (byte) 0x93, 0x35, (byte) 0xea, 0x21, 0x09, 0x40,
                0xF, 0xF, 0xF, 0xF};
        LuaDouble result = (LuaDouble) function.call(new ByteArrayLuaTable(data), LuaValue.valueOf(2));

        Assertions.assertTrue(Math.abs(LuaValue.valueOf(3.14156).checkdouble() - result.checkdouble()) < epsilon);
    }

}
package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

class SplitFunctionTest {

    @Test
    public void general() {
        SplitFunction function = new SplitFunction();

        byte[] data = {(byte) 0x0A, (byte) 0xAA, (byte) 0x0B, (byte) 0xBB, (byte) 0x0C, (byte) 0xCC, (byte) 0x0D, (byte) 0xDD};

        LuaValue[] array = {
                new ByteArrayLuaTable(data),
                LuaValue.valueOf(0),    // position
                LuaValue.valueOf(8),    // length
                LuaValue.valueOf(2)     // chunk size
        };

        LuaTable result = (LuaTable) function.invoke(LuaValue.varargsOf(array));

        Assertions.assertEquals(4, result.length());

        ByteArrayLuaTable chunk_1 = (ByteArrayLuaTable) result.get(1);
        ByteArrayLuaTable chunk_2 = (ByteArrayLuaTable) result.get(2);
        ByteArrayLuaTable chunk_3 = (ByteArrayLuaTable) result.get(3);
        ByteArrayLuaTable chunk_4 = (ByteArrayLuaTable) result.get(4);

        Assertions.assertArrayEquals(new byte[]{(byte) 0x0A, (byte) 0xAA}, (byte[]) chunk_1.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0B, (byte) 0xBB}, (byte[]) chunk_2.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0C, (byte) 0xCC}, (byte[]) chunk_3.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0D, (byte) 0xDD}, (byte[]) chunk_4.getBytes());
    }

    @Test
    public void general_with_position_and_length() {
        SplitFunction function = new SplitFunction();

        byte[] data = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0A, (byte) 0xAA, 0x0B, (byte) 0xBB, 0x0C, (byte) 0xCC, 0x0D, (byte) 0xDD, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        LuaValue[] array = {
                new ByteArrayLuaTable(data),
                LuaValue.valueOf(3),    // position
                LuaValue.valueOf(8),    // length
                LuaValue.valueOf(2)     // chunk size
        };

        LuaTable result = (LuaTable) function.invoke(LuaValue.varargsOf(array));

        Assertions.assertEquals(4, result.length());

        ByteArrayLuaTable chunk_1 = (ByteArrayLuaTable) result.get(1);
        ByteArrayLuaTable chunk_2 = (ByteArrayLuaTable) result.get(2);
        ByteArrayLuaTable chunk_3 = (ByteArrayLuaTable) result.get(3);
        ByteArrayLuaTable chunk_4 = (ByteArrayLuaTable) result.get(4);

        Assertions.assertArrayEquals(new byte[]{(byte) 0x0A, (byte) 0xAA}, (byte[]) chunk_1.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0B, (byte) 0xBB}, (byte[]) chunk_2.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0C, (byte) 0xCC}, (byte[]) chunk_3.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0x0D, (byte) 0xDD}, (byte[]) chunk_4.getBytes());
    }

    @Test
    public void general_with_position_length_remaining() {
        SplitFunction function = new SplitFunction();

        byte[] data = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                //
                (byte) 0x0A, (byte) 0xAA,
                (byte) 0x0B, (byte) 0xBB,
                (byte) 0x0C, (byte) 0xCC,
                (byte) 0x0D, (byte) 0xDD,
                //
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        LuaValue[] array = {
                new ByteArrayLuaTable(data),
                LuaValue.valueOf(3),    // position
                LuaValue.valueOf(8),    // length
                LuaValue.valueOf(3)     // chunk size
        };

        LuaTable result = (LuaTable) function.invoke(LuaValue.varargsOf(array));

        Assertions.assertEquals(2, result.length());

        ByteArrayLuaTable chunk_1 = (ByteArrayLuaTable) result.get(1);
        ByteArrayLuaTable chunk_2 = (ByteArrayLuaTable) result.get(2);

        Assertions.assertArrayEquals(new byte[]{(byte) 0x0A, (byte) 0xAA, (byte) 0x0B}, (byte[]) chunk_1.getBytes());
        Assertions.assertArrayEquals(new byte[]{(byte) 0xBB, (byte) 0x0C, (byte) 0xCC}, (byte[]) chunk_2.getBytes());
    }

}
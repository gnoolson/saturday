package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;


class SetInt8SequenceFunctionTest {

    @Test
    public void general() {
        SetInt8SequenceFunction function = new SetInt8SequenceFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[8]);

        LuaTable sequence = new LuaTable();
        sequence.set(1, LuaValue.valueOf(-25));
        sequence.set(2, LuaValue.valueOf(-13));
        sequence.set(3, LuaValue.valueOf(-5));
        sequence.set(4, LuaValue.valueOf(0));
        sequence.set(5, LuaValue.valueOf(12));
        sequence.set(6, LuaValue.valueOf(50));
        sequence.set(7, LuaValue.valueOf(78));
        sequence.set(8, LuaValue.valueOf(127));

        function.call(self, sequence, LuaValue.valueOf(0));

        byte[] bytes = self.getBytes();

        for (int i = 0; i < sequence.length(); i++) {
            int actualValue = sequence.get(i + 1).toint();
            Assertions.assertEquals(bytes[i], actualValue);
        }
    }

    @Test
    public void position() {
        SetInt8SequenceFunction function = new SetInt8SequenceFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[8]);

        LuaTable sequence = new LuaTable();
        sequence.set(1, LuaValue.valueOf(-25));
        sequence.set(2, LuaValue.valueOf(-13));
        sequence.set(3, LuaValue.valueOf(-5));
        sequence.set(4, LuaValue.valueOf(0));
        sequence.set(5, LuaValue.valueOf(12));
        sequence.set(6, LuaValue.valueOf(127));

        function.call(self, sequence, LuaValue.valueOf(2));

        byte[] bytes = self.getBytes();

        for (int i = 0, j = 2; i < sequence.length(); i++, j++) {
            int actualValue = sequence.get(i + 1).toint();
            Assertions.assertEquals(bytes[j], actualValue);
        }
    }

    @Test
    public void fail() {
        SetInt8SequenceFunction function = new SetInt8SequenceFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[8]);

        LuaTable sequence = new LuaTable();
        sequence.set(1, LuaValue.valueOf(255));


        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            function.call(self, sequence, LuaValue.valueOf(0));
        });
    }

}
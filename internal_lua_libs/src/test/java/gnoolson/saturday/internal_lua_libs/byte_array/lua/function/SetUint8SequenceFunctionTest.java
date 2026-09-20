package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

class SetUint8SequenceFunctionTest {

    @Test
    public void general() {
        SetUint8SequenceFunction function = new SetUint8SequenceFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[8]);

        LuaTable sequence = new LuaTable();
        sequence.set(1, LuaValue.valueOf(30));
        sequence.set(2, LuaValue.valueOf(60));
        sequence.set(3, LuaValue.valueOf(90));
        sequence.set(4, LuaValue.valueOf(0));
        sequence.set(5, LuaValue.valueOf(120));
        sequence.set(6, LuaValue.valueOf(150));
        sequence.set(7, LuaValue.valueOf(180));
        sequence.set(8, LuaValue.valueOf(230));

        function.call(self, sequence, LuaValue.valueOf(0));

        byte[] bytes = self.getBytes();

        for (int i = 0; i < sequence.length(); i++) {
            byte actualValue = sequence.get(i + 1).tobyte();
            Assertions.assertEquals(bytes[i], actualValue);
        }
    }

    @Test
    public void position() {
        SetUint8SequenceFunction function = new SetUint8SequenceFunction();

        ByteArrayLuaTable self = new ByteArrayLuaTable(new byte[8]);

        LuaTable sequence = new LuaTable();
        sequence.set(1, LuaValue.valueOf(90));
        sequence.set(2, LuaValue.valueOf(0));
        sequence.set(3, LuaValue.valueOf(120));
        sequence.set(4, LuaValue.valueOf(150));
        sequence.set(5, LuaValue.valueOf(180));
        sequence.set(6, LuaValue.valueOf(230));

        function.call(self, sequence, LuaValue.valueOf(2));

        byte[] bytes = self.getBytes();

        for (int i = 0, j = 2; i < sequence.length(); i++, j++) {
            byte actualValue = sequence.get(i + 1).tobyte();
            Assertions.assertEquals(bytes[j], actualValue);
        }
    }

}
package gnoolson.saturday.internal_lua_libs.byte_array.lua.function;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class SplitFunction extends VarArgFunction {

    /*
     *
     *
     * */
    private static byte[] range(byte[] byteArray, int position, int length) {
        return Arrays.copyOfRange(byteArray, position, position + length);
    }

    private static List<byte[]> split(byte[] bytes, int chunkSize) {
        List<byte[]> chunks = new ArrayList<>();
        int left = bytes.length;
        int position = 0;
        while (left >= chunkSize) {
            byte[] result = Arrays.copyOfRange(bytes, position, position + chunkSize);
            chunks.add(result);
            position += chunkSize;
            left -= chunkSize;
        }
        return chunks;
    }

    private static LuaTable chunksToLuaTable(List<byte[]> chunks) {
        LuaTable luaTable = new LuaTable();

        for (int i = 0; i < chunks.size(); i++) {
            luaTable.set(i + 1, new ByteArrayLuaTable(chunks.get(i)));
        }

        return luaTable;
    }

    @Override
    public Varargs invoke(Varargs args) {
        ByteArrayLuaTable byteArrayLuaTable = LuaArgUtils.getSelfFromFunctionArgs(args);

        LuaValue positionLuaValue = LuaArgUtils.getLuaValueFromFunctionVarargs(args, 2, "position");
        int position = LuaArgUtils.getIntFromFunctionArgs(positionLuaValue, 2, "position");

        LuaValue lengthLuaValue = LuaArgUtils.getLuaValueFromFunctionVarargs(args, 3, "length");
        int length = LuaArgUtils.getIntFromFunctionArgs(lengthLuaValue, 3, "length");

        LuaValue chunkSizeLuaValue = LuaArgUtils.getLuaValueFromFunctionVarargs(args, 4, "chunkSize");
        int chunkSize = LuaArgUtils.getIntFromFunctionArgs(chunkSizeLuaValue, 4, "chunkSize");

        if ((position + length) > byteArrayLuaTable.getBytes().length)
            throw new IllegalArgumentException("Position + length must not exceed byte array length"); // +

        byte[] buffer = range(byteArrayLuaTable.getBytes(), position, length);
        List<byte[]> chunks = split(buffer, chunkSize);

        return chunksToLuaTable(chunks);

    }

}

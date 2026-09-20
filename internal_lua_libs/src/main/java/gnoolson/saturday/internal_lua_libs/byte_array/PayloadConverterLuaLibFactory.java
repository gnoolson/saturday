package gnoolson.saturday.internal_lua_libs.byte_array;

import gnoolson.saturday.internal_lua_libs.byte_array.lua.ByteArrayLuaTable;
import gnoolson.saturday.lua_script_executor.lib.LuaLib;
import gnoolson.saturday.lua_script_executor.lib.LuaLibFactory;

public class PayloadConverterLuaLibFactory implements LuaLibFactory {

    private final ByteArrayLuaTable byteArrayLuaTable = new ByteArrayLuaTable(new byte[0]);

    /*
     *
     *
     * */
    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public LuaLib getInstance() {
        return byteArrayLuaTable;
    }

}

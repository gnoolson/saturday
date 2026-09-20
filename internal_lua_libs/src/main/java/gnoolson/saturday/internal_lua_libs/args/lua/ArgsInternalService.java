package gnoolson.saturday.internal_lua_libs.args.lua;

import gnoolson.luaj_utils.LuaTableUtils;
import gnoolson.luaj_utils.LuaTypeConverter;
import gnoolson.saturday.internal_lua_libs.args.Args;
import gnoolson.saturday.internal_lua_libs.args.Id;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import org.luaj.vm2.LuaTable;

import java.util.Map;

public class ArgsInternalService extends LuaTable implements InternalServiceLuaTable {

    private Map<String, Object> data;

    /*
     *
     *
     * */
    @Override
    public String name() {
        return "Args";
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        Args args = (Args) functionality;

        LuaTableUtils.clear(this);

        Map<String, Object> data = args.get();
        LuaTable result = (LuaTable) LuaTypeConverter.toLua(data);
        LuaTableUtils.merge(result, this);
    }

    @Override
    public void release() {

    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

}

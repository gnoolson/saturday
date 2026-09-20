package gnoolson.saturday.internal_lua_libs.output.lua;

import gnoolson.luaj_utils.LuaTypeConverter;
import gnoolson.saturday.internal_lua_libs.output.Id;
import gnoolson.saturday.internal_lua_libs.output.Output;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class OutputInternalService extends LuaTable implements InternalServiceLuaTable {

    private Output output;

    /*
     *
     *
     * */
    public OutputInternalService() {
        set("print", new PrintlnFunction(this));
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    @Override
    public String name() {
        return "Output";
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        output = (Output) functionality;
    }

    @Override
    public void release() {

    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class PrintlnFunction extends VarArgFunction {

        private final OutputInternalService outputInternalService;

        @Override
        public Varargs invoke(Varargs args) {
            String[] strings = LuaTypeConverter.toStrings(args, 1);
            outputInternalService.output.print(strings);
            return LuaValue.NIL;
        }
    }

}

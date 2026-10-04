package gnoolson.saturday.internal_lua_libs.log.lua;

import gnoolson.luaj_utils.LuaTypeConverter;
import gnoolson.saturday.internal_lua_libs.log.Id;
import gnoolson.saturday.internal_lua_libs.log.Log;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LogInternalService extends LuaTable implements InternalServiceLuaTable {

    private Log log;

    /*
     *
     *
     * */
    public LogInternalService() {
        set("debug", new InfoFunction(this, InfoFunction.Type.DEBUG));
        set("info", new InfoFunction(this, InfoFunction.Type.INFO));
        set("warn", new InfoFunction(this, InfoFunction.Type.WARN));
        set("error", new InfoFunction(this, InfoFunction.Type.ERROR));
        set("isDebugEnabled", new IsDebugEnabledFunction(this));
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        this.log = (Log) functionality;
    }

    @Override
    public void release() {

    }

    @Override
    public String name() {
        return "Log";
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class InfoFunction extends VarArgFunction {
        private final LogInternalService logInternalService;
        private final Type type;

        @Override
        public Varargs invoke(Varargs args) {
            String[] strings = LuaTypeConverter.toStrings(args, 1);

            switch (type) {
                case DEBUG:
                    logInternalService.log.debug(strings);
                    break;
                case INFO:
                    logInternalService.log.info(strings);
                    break;
                case WARN:
                    logInternalService.log.warn(strings);
                    break;
                case ERROR:
                    logInternalService.log.error(strings);
                    break;
            }

            return LuaValue.NIL;
        }

        enum Type {
            DEBUG,
            INFO,
            WARN,
            ERROR
        }
    }

    @RequiredArgsConstructor
    public static class IsDebugEnabledFunction extends ZeroArgFunction {
        private final LogInternalService logInternalService;

        @Override
        public LuaValue call() {
            return LuaBoolean.valueOf(logInternalService.log.isDebugEnabled());
        }

    }

}

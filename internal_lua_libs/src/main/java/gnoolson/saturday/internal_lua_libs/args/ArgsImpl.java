package gnoolson.saturday.internal_lua_libs.args;

import gnoolson.saturday.lua_script_executor.Output;

import java.util.Map;

public class ArgsImpl implements Args {

    private Map<String, Object> data;

    /*
     *
     *
     * */
    @Override
    public void setup(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public Map<String, Object> get() {
        if (data == null)
            throw new IllegalStateException("Args data is not initialized"); // +

        return data;
    }

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public void writeResultToOutput(Output output) {

    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

}

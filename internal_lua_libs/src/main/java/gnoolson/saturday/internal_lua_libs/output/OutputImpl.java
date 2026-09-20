package gnoolson.saturday.internal_lua_libs.output;

import gnoolson.saturday.lua_script_executor.utils.LogUtil;

public class OutputImpl implements Output {

    private gnoolson.saturday.lua_script_executor.Output output;

    /*
     *
     *
     * */
    @Override
    public void print(String... msgs) {
        if (output == null)
            return;

        output.println(LogUtil.stringArrayToSingleString(msgs));
    }

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public void writeResultToOutput(gnoolson.saturday.lua_script_executor.Output output) {
        this.output = output;
    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

}

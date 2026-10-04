package gnoolson.saturday.internal_lua_libs.log;

import gnoolson.saturday.lua_script_executor.Output;
import gnoolson.saturday.lua_script_executor.utils.LogUtil;

public class LogImpl implements Log {

    private Output output;
    private boolean useOutput;
    private LogGateway logGateway;
    private boolean debug;

    /*
     *
     *
     * */
    @Override
    public void info(String... msgs) {
        if (this.useOutput) {
            output.println("-- Log.info()");
            output.println(LogUtil.stringArrayToSingleString(msgs));
            output.println();
        } else {
            if (logGateway == null)
                throw new IllegalStateException("LogGateway was not found");
            logGateway.info(msgs);
        }
    }

    @Override
    public void warn(String... msgs) {
        if (this.useOutput) {
            output.println("-- Log.warn()");
            output.println(LogUtil.stringArrayToSingleString(msgs));
            output.println();
        } else {
            if (logGateway == null)
                throw new IllegalStateException("LogGateway was not found");
            logGateway.warn(msgs);
        }
    }

    @Override
    public void error(String... msgs) {
        if (this.useOutput) {
            output.println("-- Log.error()");
            output.println(LogUtil.stringArrayToSingleString(msgs));
            output.println();
        } else {
            if (logGateway == null)
                throw new IllegalStateException("LogGateway was not found");
            logGateway.error(msgs);
        }
    }

    @Override
    public void debug(String... msgs) {
        if (!debug)
            return;

        if (this.useOutput) {
            output.println("-- Log.debug()");
            output.println(LogUtil.stringArrayToSingleString(msgs));
            output.println();
        } else {
            if (logGateway == null)
                throw new IllegalStateException("LogGateway was not found");
            logGateway.debug(msgs);
        }
    }

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public void writeResultToOutput(Output output) {
        this.output = output;
    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

    @Override
    public void useOutput(boolean flag) {
        this.useOutput = flag;
    }

    @Override
    public void setup(LogGateway logGateway) {
        this.logGateway = logGateway;
        this.debug = logGateway.isDebugEnabled();
    }

    @Override
    public void setup(LogGateway logGateway, boolean debug) {
        this.logGateway = logGateway;
        this.debug = debug;
    }

    @Override
    public boolean isDebugEnabled() {
        return debug;
    }

}

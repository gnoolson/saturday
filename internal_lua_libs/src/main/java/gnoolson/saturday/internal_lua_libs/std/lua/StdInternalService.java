package gnoolson.saturday.internal_lua_libs.std.lua;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.std.Id;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class StdInternalService extends LuaTable implements InternalServiceLuaTable {

    private Globals globals;

    /*
     *
     *
     * */
    public StdInternalService() {
        set("captureRowOut", new CaptureRowFunction(this, false));
        set("captureRowErr", new CaptureRowFunction(this, true));
        set("captureByteOut", new CaptureByteFunction(this, false));
        set("captureByteErr", new CaptureByteFunction(this, true));
        set("restoreOut", new RestoreFunction(this, false));
        set("restoreErr", new RestoreFunction(this, true));
    }

    public static PrintStream createPrintStreamForRow(LuaFunction callback) {
        try {
            return new PrintStream(new OutputStream() {
                private final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                @Override
                public void write(int b) throws IOException {
                    if (b == '\n') {
//                      byteBuffer.write(b);
                        String line = byteBuffer.toString(StandardCharsets.UTF_8.name());
                        callback.call(line);

                        byteBuffer.reset();
                    } else if (b != '\r') {
                        byteBuffer.write(b);
                    }
                }

                @Override
                public void close() throws IOException {
                    if (byteBuffer.size() > 0) {
                        String line = byteBuffer.toString(StandardCharsets.UTF_8.name());
                        callback.call(line);
                        byteBuffer.reset();
                    }
                    byteBuffer.close();
                }
            }, true, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrintStream createPrintStreamForByte(LuaFunction callback) {
        try {
            return new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    callback.call(LuaValue.valueOf(b));
                }

            }, true, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
    }

    @Override
    public void release() {
    }

    @Override
    public String name() {
        return "Std";
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    @Override
    public void setGlobals(Globals globals) {
        this.globals = globals;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class CaptureRowFunction extends OneArgFunction {
        private final StdInternalService stdInternalService;
        private final boolean err;

        @Override
        public LuaValue call(LuaValue callbackLuaValue) {
            LuaFunction callback = LuaArgUtils.getFunctionFromFunctionArgs(callbackLuaValue, 1, "callback");
            PrintStream printStreamForCallback = createPrintStreamForRow(callback);

            if (err) {
                stdInternalService.globals.STDERR = printStreamForCallback;
            } else {
                stdInternalService.globals.STDOUT = printStreamForCallback;
            }
            return LuaValue.NIL;
        }
    }

    @RequiredArgsConstructor
    public static class CaptureByteFunction extends OneArgFunction {
        private final StdInternalService stdInternalService;
        private final boolean err;

        @Override
        public LuaValue call(LuaValue callbackLuaValue) {
            LuaFunction callback = LuaArgUtils.getFunctionFromFunctionArgs(callbackLuaValue, 1, "callback");
            PrintStream printStreamForCallback = createPrintStreamForByte(callback);

            if (err) {
                stdInternalService.globals.STDERR = printStreamForCallback;
            } else {
                stdInternalService.globals.STDOUT = printStreamForCallback;
            }
            return LuaValue.NIL;
        }
    }

    @RequiredArgsConstructor
    public static class RestoreFunction extends ZeroArgFunction {
        private final StdInternalService stdInternalService;
        private final boolean err;

        @Override
        public LuaValue call() {
            if (err) {
                stdInternalService.globals.STDERR = System.err;
            } else {
                stdInternalService.globals.STDOUT = System.out;
            }

            return LuaValue.NIL;
        }
    }


}

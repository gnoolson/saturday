package gnoolson.saturday.lua_script_executor;

import gnoolson.saturday.lua_script_executor.utils.LuaErrorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

@Log4j2
@RequiredArgsConstructor
public class LuaScriptExecutorLuajImpl implements LuaScriptExecutor {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final long maxExecutionTime;

    /*
     *
     *
     * */
    @Override
    public long execute(Sandbox sandbox) {
        Callable<Long> task = () -> {
            long now = System.currentTimeMillis();

            try {
                sandbox.executeScript();
                long executionTime = System.currentTimeMillis() - now;
                if (log.isDebugEnabled()) {
                    log.debug("\"{}\" execution time {} ms", sandbox.getScriptName(), executionTime);
                }

                return executionTime;
            } finally {
                sandbox.release();
            }
        };

        Future<Long> future = executor.submit(task);

        try {
            return future.get(maxExecutionTime, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new RuntimeException(String.format("Script execution time limit exceeded (%d ms)", maxExecutionTime)); // +
        } catch (Exception e) {
            throw LuaErrorUtil.getLuaError(e); // +
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            executor.shutdownNow();
        } finally {
            super.finalize();
        }
    }

}

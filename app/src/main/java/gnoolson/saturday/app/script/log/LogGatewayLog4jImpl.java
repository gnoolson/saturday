package gnoolson.saturday.app.script.log;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.lua_script_executor.utils.LogUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
public class LogGatewayLog4jImpl implements LogGateway {

    private final String pathToDebugFlagFile;

    private final Logger logger;
    private final LoggerContext ctx;
    private final Configuration config;
    private final RollingFileAppender appender;
    private final String name;
    private boolean debug;

    /*
     *
     *
     * */
    public LogGatewayLog4jImpl(ScriptId scriptId, File folder) {
        ctx = (LoggerContext) LogManager.getContext(false);
        config = ctx.getConfiguration();
        name = "script_" + scriptId.getValue();

        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5level- %msg%n")
                .withConfiguration(config)
                .build();

        SizeBasedTriggeringPolicy sizePolicy = SizeBasedTriggeringPolicy.createPolicy("1MB");
//        TimeBasedTriggeringPolicy timePolicy = TimeBasedTriggeringPolicy.newBuilder()
//                .withInterval(1)
//                .withModulate(true)
//                .build();

        DefaultRolloverStrategy strategy = DefaultRolloverStrategy.newBuilder()
                .withMax("20")
                .withConfig(config)
                .build();

        CompositeTriggeringPolicy policy = CompositeTriggeringPolicy.createPolicy(sizePolicy /*, timePolicy*/);

        String folderName = String.valueOf(scriptId.getValue());
        String pathToFolder = folder.getAbsolutePath() + File.separator + folderName;
        String pathToLogFile = pathToFolder + File.separator + "script.log";
        String pathToArchiverFile = pathToFolder + File.separator + "%d{yyyy-MM-dd-HH-mm}-%i.script.log.gz";

        appender = RollingFileAppender.newBuilder()
                .setName(name)
                .withFileName(pathToLogFile)
                .setConfiguration(config)
                .setLayout(layout)
                .withPolicy(policy)
                .withFilePattern(pathToArchiverFile)
                .withStrategy(strategy)
                .withAppend(true)
                .build();

        appender.start();
        config.addAppender(appender);

        AppenderRef[] refs = new AppenderRef[]{};
        LoggerConfig loggerConfig = LoggerConfig.createLogger(
                false,
                org.apache.logging.log4j.Level.DEBUG,
                LogManager.ROOT_LOGGER_NAME,
                "true",
                refs,
                null,
                config,
                null
        );

        loggerConfig.addAppender(appender, null, null);
        config.addLogger(name, loggerConfig);
        ctx.updateLoggers();
        logger = LogManager.getLogger(name);

        pathToDebugFlagFile = pathToFolder + File.separator + "debug";
        debug = new File(pathToDebugFlagFile).exists();
    }

    @Override
    public synchronized boolean isDebugEnabled() {
        return debug;
    }

    @Override
    public synchronized void setDebugEnabled(boolean flag) {
        if (flag == debug)
            return;

        File debugFlagFile = new File(pathToDebugFlagFile);
        try {
            if (flag && !debugFlagFile.exists()) {
                debugFlagFile.createNewFile();
            } else {
                debugFlagFile.delete();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        debug = flag;
    }

    @Override
    public synchronized void close() {
        appender.stop();
        config.removeLogger(name);
        ctx.updateLoggers();
    }

    @Override
    public synchronized boolean clear() {
        File file = new File(appender.getFileName());
        if (file.exists()) {
            try {
                FileUtils.write(file, Strings.EMPTY, StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.warn("Exception", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public synchronized void info(String... msgs) {
        String msg = LogUtil.stringArrayToSingleString(msgs);
        logger.info(msg);
    }

    @Override
    public synchronized void warn(String... msgs) {
        String msg = LogUtil.stringArrayToSingleString(msgs);
        logger.warn(msg);
    }

    @Override
    public synchronized void error(String... msgs) {
        String msg = LogUtil.stringArrayToSingleString(msgs);
        logger.error(msg);
    }

    @Override
    public void debug(String... msgs) {
        if (!debug)
            return;

        String msg = LogUtil.stringArrayToSingleString(msgs);
        logger.debug(msg);
    }

}

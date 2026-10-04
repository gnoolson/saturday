package gnoolson.saturday.app.script.log;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.script.port.outbound.ScriptLogReaderGateway;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@RequiredArgsConstructor
public class SaturdayLogImpl implements SaturdayLog {

    private final File logFile;
    private final ScriptLogReaderGateway scriptLogReaderGateway;

    /*
     *
     *
     * */
    @Override
    public String getText(PositiveNumber rows) {
        if (logFile.exists())
            return scriptLogReaderGateway.execute(logFile, rows.getValue());

        return StringUtils.EMPTY;
    }

}

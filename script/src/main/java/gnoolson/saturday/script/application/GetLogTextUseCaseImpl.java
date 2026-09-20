package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.port.inbound.GetLogTextUseCase;
import gnoolson.saturday.script.port.outbound.ScriptLogReaderGateway;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
public class GetLogTextUseCaseImpl implements GetLogTextUseCase {

    private final File pathToLogFolder;
    private final ScriptLogReaderGateway scriptLogReaderGateway;

    /*
     *
     *
     * */
    @Override
    public Optional<String> execute(ScriptId scriptId, PositiveNumber rows) {
        String folderName = String.valueOf(scriptId.getValue());
        String pathToLogFile = pathToLogFolder.getAbsolutePath() + File.separator + folderName + File.separator + "script.log";

        File file = new File(pathToLogFile);
        if (file.exists())
            return Optional.of(scriptLogReaderGateway.execute(file, rows.getValue()));

        return Optional.empty();
    }

}

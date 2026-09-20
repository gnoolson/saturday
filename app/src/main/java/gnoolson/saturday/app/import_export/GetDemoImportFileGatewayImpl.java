package gnoolson.saturday.app.import_export;

import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.port.outbound.GetDemoImportFileGateway;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
public class GetDemoImportFileGatewayImpl implements GetDemoImportFileGateway {

    private final String DEMO_FILE_NAME = "demo.zip";
    private final File folder;

    /*
     *
     *
     * */
    @Override
    public Optional<ImportFile> execute() {
        if (!folder.exists())
            return Optional.empty();

        File[] files = folder.listFiles();
        if (files == null)
            return Optional.empty();

        for (File file : files) {
            if (file.getName().equals(DEMO_FILE_NAME)) {
                try {
                    return Optional.of(ImportFile.of(FileUtils.readFileToByteArray(file)));
                } catch (Exception e) {
                    throw new RuntimeException(e); // +
                }
            }
        }

        return Optional.empty();
    }

}

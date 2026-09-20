package gnoolson.saturday.app.import_export;

import gnoolson.saturday.export_import.model.vo.FileName;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DownloadExportFile {

    public File execute(FileName fileName) {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        File file = new File(tempDirectoryPath + File.separator + fileName.getValue());

        if (!file.exists())
            throw new RuntimeException("File was not found"); // +

        return file;
    }

}

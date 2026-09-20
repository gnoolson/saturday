package gnoolson.saturday.app.import_export;

import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.port.outbound.UnzipImportFilesGateway;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class UnzipImportFilesGatewayImpl implements UnzipImportFilesGateway {

    @Override
    public Path execute(ImportFile importFile) {

        try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(importFile.getBytes()))) {
            java.nio.file.Path pathToFiles = Files.createTempDirectory("saturday.import.");

            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = pathToFiles.toAbsolutePath().toString() + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

            return Path.of(pathToFiles.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e); // +
        }
    }

    /*
     *
     *
     * */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        java.nio.file.Path file = Paths.get(filePath);
        java.nio.file.Path parentDir = file.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                fos.write(bytesIn, 0, read);
            }
        }
    }

}

package gnoolson.saturday.app.import_export;

import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.export_import.model.vo.FileName;
import gnoolson.saturday.export_import.port.outbound.ZipExportFilesGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Component
public class ZipExportFilesGatewayImpl implements ZipExportFilesGateway {

    private final TimeProvider timeProvider;

    /*
     *
     *
     * */
    @Override
    public FileName execute(List<File> filesToZip) {
        try {
            String fileName = getFileName();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            File zipFile = new File(tempDirectoryPath + File.separator + fileName);
            Files.createFile(zipFile.toPath());
            byte[] buffer = new byte[1024];

            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                for (File file : filesToZip) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zos.putNextEntry(zipEntry);

                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                    }
                }
            }
            return FileName.of(zipFile.getName());
        } catch (IOException e) {
            throw new RuntimeException(e); // +
        }
    }

    /*
     *
     *
     * */
    private String getFileName() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeProvider.now().getValue());

        return String.format("saturday_%d%s%s_%s%s%s.zip",
                calendar.get(Calendar.YEAR),
                addZeroIfNeeds(calendar.get(Calendar.MONTH)),
                addZeroIfNeeds(calendar.get(Calendar.DAY_OF_MONTH)),
                addZeroIfNeeds(calendar.get(Calendar.HOUR_OF_DAY)),
                addZeroIfNeeds(calendar.get(Calendar.MINUTE)),
                addZeroIfNeeds(calendar.get(Calendar.SECOND))
        );
    }

    private String addZeroIfNeeds(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }

//    private String tempFileNameToNormalFileName(String tempFileName) {
//        int firstMark = tempFileName.indexOf(TempFileMarks.FIRST_MARK);
//        int secondMark = tempFileName.indexOf(TempFileMarks.SECOND_MARK, firstMark+1);
//
//        StringBuilder sb = new StringBuilder(tempFileName);
//        sb.delete(firstMark, secondMark + 1);
//
//        return sb.toString();
//    }


}

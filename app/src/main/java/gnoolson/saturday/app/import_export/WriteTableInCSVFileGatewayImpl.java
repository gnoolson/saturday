package gnoolson.saturday.app.import_export;

import com.opencsv.CSVWriter;
import gnoolson.saturday.export_import.model.vo.FieldName;
import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.model.vo.Row;
import gnoolson.saturday.export_import.model.vo.TableName;
import gnoolson.saturday.export_import.port.outbound.WriteTableInCSVFileGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteTableInCSVFileGatewayImpl implements WriteTableInCSVFileGateway {

    @Override
    public File execute(Path tempDir, TableName name, List<FieldName> fields, List<Row> rows) {
        try {
            File file = Files.createFile(new File(tempDir.getValue() + File.separator + name + ".csv").toPath()).toFile();
            FileWriter fileWriter = new FileWriter(file);

            try (CSVWriter writer = new CSVWriter(fileWriter)) {
                String[] fieldsLikeStringArray = fields.stream().map(fieldName -> fieldName.getName()).toArray(String[]::new);
                writer.writeNext(fieldsLikeStringArray);

                for (Row row : rows) {
                    String[] rowLikeStringArray = row.getValues().toArray(new String[0]);
                    writer.writeNext(rowLikeStringArray);
                }
            }

            return file;
        } catch (IOException e) {
            throw new RuntimeException(e); // +
        }
    }

}

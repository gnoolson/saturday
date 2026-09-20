package gnoolson.saturday.app.import_export;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import gnoolson.saturday.export_import.model.vo.*;
import gnoolson.saturday.export_import.port.outbound.ReadImportFileGateway;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadImportFileGatewayImpl implements ReadImportFileGateway {

    @Override
    public Table execute(TableName tableName, Path path) {
        String filePath = path.getValue() + File.separator + tableName.toString() + ".csv";

        List<FieldName> fieldNames;
        List<Row> rows = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;

            String[] header = reader.readNext();
            fieldNames = Arrays.asList(header).stream().map(FieldName::of).collect(Collectors.toList());

            while ((nextRecord = reader.readNext()) != null) {
                Row row = new Row(Arrays.asList(nextRecord));
                rows.add(row);
            }

        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(String.format("File \"%s\" was not found", tableName.toString() + ".csv")); // +
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e); // +
        }

        return new Table(tableName, fieldNames, rows);
    }

}

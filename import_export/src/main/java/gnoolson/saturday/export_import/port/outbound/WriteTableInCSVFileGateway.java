package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.FieldName;
import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.model.vo.Row;
import gnoolson.saturday.export_import.model.vo.TableName;

import java.io.File;
import java.util.List;


public interface WriteTableInCSVFileGateway {

    File execute(Path tempDir, TableName name, List<FieldName> fields, List<Row> rows);

}

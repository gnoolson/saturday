package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.model.vo.Table;
import gnoolson.saturday.export_import.model.vo.TableName;

public interface ReadImportFileGateway {

    Table execute(TableName tableName, Path path);

}

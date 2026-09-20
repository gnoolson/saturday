package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.model.vo.Path;

public interface UnzipImportFilesGateway {

    Path execute(ImportFile importFile);

}

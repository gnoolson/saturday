package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.ImportFile;

import java.util.Optional;

public interface GetDemoImportFileGateway {

    Optional<ImportFile> execute();

}

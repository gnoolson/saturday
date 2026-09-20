package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.FileName;

import java.io.File;
import java.util.List;


public interface ZipExportFilesGateway {

    FileName execute(List<File> filesToZip);

}

package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.export_import.model.vo.Path;


public interface DeleteTempFolderGateway {

    void execute(Path path);

}

package gnoolson.saturday.app.import_export;

import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.port.outbound.CreateTempDirForExportGateway;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class CreateTempDirForExportGatewayImpl implements CreateTempDirForExportGateway {

    @Override
    public Path execute() {
        try {
            return Path.of(Files.createTempDirectory("saturday.export.").toAbsolutePath().toString());
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}

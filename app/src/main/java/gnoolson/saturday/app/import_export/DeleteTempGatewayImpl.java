package gnoolson.saturday.app.import_export;

import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.port.outbound.DeleteTempFolderGateway;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

@Component
public class DeleteTempGatewayImpl implements DeleteTempFolderGateway {

    @Override
    public void execute(Path path) {
        try {
            Files.walk(new File(path.getValue()).toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(java.nio.file.Path::toFile)
                    .forEach(java.io.File::delete);

        } catch (IOException e) {
            throw new RuntimeException(e); // +
        }
    }

}

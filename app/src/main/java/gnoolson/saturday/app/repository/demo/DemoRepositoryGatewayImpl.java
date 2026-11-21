package gnoolson.saturday.app.repository.demo;

import gnoolson.saturday.export_import.port.outbound.DemoRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class DemoRepositoryGatewayImpl implements DemoRepositoryGateway {

    private final String FILE_NAME = "demo";
    private final File configFolder;

    /*
     *
     *
     * */
    @Override
    public boolean get() {
        File file = new File(configFolder.getAbsolutePath() + File.separator + FILE_NAME);
        return file.exists();
    }

    @Override
    public void save(boolean flag) {
        File file = new File(configFolder.getAbsolutePath() + File.separator + FILE_NAME);
        try {
            if(file.exists())
                return;
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

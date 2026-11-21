package gnoolson.saturday.app.repository.backup;

import java.io.IOException;

public interface DeleteUnnecessaryBackups {

    void execute() throws IOException;

}

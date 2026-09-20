package gnoolson.saturday.script.port.outbound;

import java.io.File;


public interface ScriptLogReaderGateway {

    String execute(File file, int rows);

}

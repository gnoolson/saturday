package gnoolson.saturday.app.script;

import gnoolson.saturday.script.port.outbound.ScriptLogReaderGateway;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class ScriptLogReaderGatewayImpl implements ScriptLogReaderGateway {

    @Override
    public String execute(File file, int rows) {

        try (RandomAccessFile fileHandler = new RandomAccessFile(file, "r")) {
            long fileLength = fileHandler.length() - 1;

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int row = 0;

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {

                fileHandler.seek(filePointer);
                int b = fileHandler.read();

                if (b == 0xA) {
                    if (filePointer < fileLength) row++;
                } else if (b == 0xD) {
                    if (filePointer < fileLength - 1) row++;
                }

                if (row >= rows) {
                    break;
                }

                out.write(b);
            }

            byte[] reversed = out.toByteArray();

            for (int i = 0, j = reversed.length - 1; i < j; i++, j--) {
                byte tmp = reversed[i];
                reversed[i] = reversed[j];
                reversed[j] = tmp;
            }

            return new String(reversed, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}

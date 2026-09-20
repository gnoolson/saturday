package gnoolson.saturday.app.script;

import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.outbound.BeautifyCodeGateway;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class BeautifyLuaCodeGatewayImpl implements BeautifyCodeGateway {

    private final String pathToExecutableFile;

    /*
     *
     *
     * */
    public BeautifyLuaCodeGatewayImpl(@Value("${gnoolson.saturday.script.lua.beautify_code.stylua_folder}") String path) throws FileNotFoundException {
        String absolutePath = ResourceUtils.getFile(path).getAbsolutePath();

        if (SystemUtils.IS_OS_LINUX) {
            pathToExecutableFile = absolutePath + File.separator + "stylua" + File.separator + "linux" + File.separator + "stylua";
        } else if (SystemUtils.IS_OS_WINDOWS) {
            pathToExecutableFile = absolutePath + File.separator + "stylua" + File.separator + "win" + File.separator + "stylua.exe";
        } else if (SystemUtils.IS_OS_MAC) {
            pathToExecutableFile = absolutePath + File.separator + "stylua" + File.separator + "mac" + File.separator + "stylua";
        } else {
            throw new IllegalStateException("Unsupported operating system"); // +
        }
    }

    public Code execute(Code luaCode) {
        Process process = null;
        try {
            process = getProcess();

            try (OutputStream os = process.getOutputStream()) {
                os.write(luaCode.getValue().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            String output;
            try (InputStream is = process.getInputStream()) {
                output = new String(readAllBytesJava(is), StandardCharsets.UTF_8);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0)
                throw new RuntimeException(output); // +

            return Code.of(output);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); // +
        } finally {
            if (process != null && process.isAlive()) {
                process.destroy();
            }
        }
    }

    /*
     *
     *
     * */
    private Process getProcess() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(pathToExecutableFile, "-");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        return process;
    }

    private byte[] readAllBytesJava(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] data = new byte[4096];

        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }

        return buffer.toByteArray();
    }

}

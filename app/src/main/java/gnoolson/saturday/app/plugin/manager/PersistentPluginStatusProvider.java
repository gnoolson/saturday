package gnoolson.saturday.app.plugin.manager;

import org.apache.commons.io.FileUtils;
import org.pf4j.PluginStatusProvider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PersistentPluginStatusProvider implements PluginStatusProvider {

    public static final String DELIMITER = ";";
    private final Set<String> enabled = new HashSet<>();
    private final File file;
    private boolean firstLaunch;

    /*
     *
     *
     * */
    public PersistentPluginStatusProvider(File pluginsFolder) {
        file = new File(pluginsFolder, "enabled");
        loadFile();
    }

    @Override
    public boolean isPluginDisabled(String pluginId) {
        if (firstLaunch) {
            enablePlugin(pluginId);
        }

        return !enabled.contains(pluginId);
    }

    @Override
    public void disablePlugin(String pluginId) {
        firstLaunch = false;
        enabled.remove(pluginId);
        saveFile();
    }

    @Override
    public void enablePlugin(String pluginId) {
        enabled.add(pluginId);
        saveFile();
    }

    /*
     *
     *
     * */
    private void saveFile() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String pluginId : enabled) {
            if (pluginId.isEmpty())
                continue;

            stringBuilder.append(pluginId);
            stringBuilder.append(DELIMITER);
        }

        try {
            FileUtils.write(file, stringBuilder.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

    private void loadFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                firstLaunch = true;
            } catch (IOException e) {
                throw new RuntimeException(e); // +
            }
        }

        String content = "";

        try {
            content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            if (content.isEmpty())
                return;

            String[] pluginIds = content.split(DELIMITER);
            enabled.addAll(Arrays.asList(pluginIds));
        } catch (IOException e) {
            throw new RuntimeException(e);  // +
        }
    }

}
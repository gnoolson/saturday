package gnoolson.saturday.app.plugin.manager;

import org.pf4j.BasePluginRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecursivePluginRepository extends BasePluginRepository {

    private final Path pluginsRoot;

    public RecursivePluginRepository(Path pluginsRoot) {
        super(pluginsRoot);
        this.pluginsRoot = pluginsRoot;
    }

    @Override
    public List<Path> getPluginPaths() {
        List<Path> result = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(pluginsRoot, 2)) {
            stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith("plugin.jar"))
                    .forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e); // +
        }

        return result;
    }
}
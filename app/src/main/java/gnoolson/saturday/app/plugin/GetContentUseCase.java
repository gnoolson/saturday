package gnoolson.saturday.app.plugin;

import gnoolson.saturday_plugin_api.Content;

import java.util.Map;
import java.util.Optional;

public interface GetContentUseCase {

    Optional<Content> execute(String pluginId, String language, String path, Map<String, String> params);

}

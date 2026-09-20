package gnoolson.saturday.app.plugin;

import gnoolson.saturday_plugin_api.Content;
import gnoolson.saturday_plugin_api.WebEndpoint;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

;

@RequiredArgsConstructor
public class GetContentUseCaseImpl implements GetContentUseCase {

    private final SaturdayPluginManager pluginManager;

    /*
     *
     *
     *
     * */
    @Override
    public Optional<Content> execute(String pluginId, String language, String path, Map<String, String> params) {
        Optional<WebEndpoint> webEndpointOpt = pluginManager.getWebEndpoint(pluginId);
        if (!webEndpointOpt.isPresent())
            return Optional.empty();

        WebEndpoint webEndpoint = webEndpointOpt.get();

        return Optional.of(webEndpoint.getContent(language, path, params));
    }

}

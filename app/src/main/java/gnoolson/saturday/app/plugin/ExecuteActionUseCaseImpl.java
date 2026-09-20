package gnoolson.saturday.app.plugin;

import gnoolson.saturday_plugin_api.WebEndpoint;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ExecuteActionUseCaseImpl implements ExecuteActionUseCase {

    private final SaturdayPluginManager pluginManager;

    /*
     *
     *
     * */
    @Override
    public Optional<Map<String, Object>> execute(String pluginId, Map<String, Object> requestBody) {
        Optional<WebEndpoint> webEndpointOpt = pluginManager.getWebEndpoint(pluginId);
        if (!webEndpointOpt.isPresent())
            return Optional.empty();

        WebEndpoint webEndpoint = webEndpointOpt.get();
        return Optional.of(webEndpoint.executeAction(requestBody));
    }

}

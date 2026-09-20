package gnoolson.saturday.app.plugin;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProvider;
import gnoolson.saturday.app.script.lib.LuaLibProvider;
import gnoolson.saturday_plugin_api.FunctionalityFactoryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PluginServiceImpl implements PluginService {

    private final SaturdayPluginManager pluginManager;
    private final LuaLibFunctionalityProvider functionalityProvider;
    private final LuaLibProvider luaLibProvider;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void loadPlugins() {
        List<PluginInfo> plugins = getPlugins();
        for (PluginInfo plugin : plugins) {
            loadProviders(plugin.getId());
        }
    }

    @Override
    public List<PluginInfo> getPlugins() {
        return pluginManager.getPluginInfos();
    }

    @Override
    public void control(String pluginId, boolean flag) {
        try (Locker.LockHandle ignore = locker.lockIds()) {
            if (!pluginManager.pluginExists(pluginId))
                throw new RuntimeException("Plugin was not found"); // +

            if (flag) {
                pluginManager.enablePlugin(pluginId);
                loadProviders(pluginId);
            } else {
                pluginManager.disablePlugin(pluginId);
                deleteProviders(pluginId);
            }
        }
    }

    /*
     *
     *
     * */
    private void deleteProviders(String pluginId) {
        functionalityProvider.remove(pluginId);
        luaLibProvider.remove(pluginId);
    }

    private void loadProviders(String pluginId) {
        Optional<FunctionalityFactoryProvider> functionalityFactoryProviders = pluginManager.getFunctionalityFactoryProviders(pluginId);
        functionalityFactoryProviders.ifPresent((functionalityFactoryProvider) -> {
            functionalityProvider.addFactory(pluginId, functionalityFactoryProvider.getFactory());
        });

        pluginManager.getLuaModuleFactoryProviders(pluginId).ifPresent((luaLibFactoryProvider) -> {
            luaLibProvider.addFactory(pluginId, luaLibFactoryProvider.getFactory());
        });
    }

}

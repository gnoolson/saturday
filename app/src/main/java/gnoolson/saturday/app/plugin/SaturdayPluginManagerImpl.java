package gnoolson.saturday.app.plugin;

import gnoolson.saturday.app.plugin.manager.PluginManagerImpl;
import gnoolson.saturday_plugin_api.FunctionalityFactoryProvider;
import gnoolson.saturday_plugin_api.LuaModuleFactoryProvider;
import gnoolson.saturday_plugin_api.SaturdayPlugin;
import gnoolson.saturday_plugin_api.WebEndpoint;
import lombok.RequiredArgsConstructor;
import org.pf4j.Plugin;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SaturdayPluginManagerImpl implements SaturdayPluginManager {

    private final PluginManagerImpl pluginManager;

    /*
     *
     *
     * */
    @Override
    public Optional<String> getPluginId(Class<?> clazz) {

        PluginWrapper pluginWrapper = pluginManager.whichPlugin(clazz);
        if (pluginWrapper == null)
            return Optional.empty();

        return Optional.of(pluginWrapper.getPluginId());
    }

    @Override
    public Optional<FunctionalityFactoryProvider> getFunctionalityFactoryProviders(String pluginId) {
        List<FunctionalityFactoryProvider> extensions = pluginManager.getExtensions(FunctionalityFactoryProvider.class, pluginId);
        if (extensions.size() > 1) {
            throw new RuntimeException("Plugin cannot contain more than one FunctionalityFactoryProvider"); // +
        }

        if (extensions.size() == 1) {
            return Optional.of(extensions.get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<LuaModuleFactoryProvider> getLuaModuleFactoryProviders(String pluginId) {
        List<LuaModuleFactoryProvider> extensions = pluginManager.getExtensions(LuaModuleFactoryProvider.class, pluginId);
        if (extensions.size() > 1) {
            throw new RuntimeException("Plugin cannot contain more than one LuaModuleFactoryProvider"); // +
        }

        if (extensions.size() == 1) {
            return Optional.of(extensions.get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<WebEndpoint> getWebEndpoint(String pluginId) {
        List<WebEndpoint> extensions = pluginManager.getExtensions(WebEndpoint.class, pluginId);
        if (extensions.size() > 1) {
            throw new RuntimeException("Plugin cannot contain more than one WebEndpoint"); // +
        }

        if (extensions.size() == 1) {
            return Optional.of(extensions.get(0));
        }

        return Optional.empty();
    }

    @Override
    public void enablePlugin(String pluginId) {
        pluginManager.enablePluginPersistent(pluginId);
    }

    @Override
    public void disablePlugin(String pluginId) {
        pluginManager.disablePluginPersistent(pluginId);
    }

    @Override
    public boolean pluginExists(String pluginId) {
        return pluginManager.getPlugin(pluginId) != null;
    }

    @Override
    public List<PluginInfo> getPluginInfos() {
        return pluginManager.getPlugins().stream().map((pluginWrapper) -> {
            PluginDescriptor descriptor = pluginWrapper.getDescriptor();

            return new PluginInfo(
                    descriptor.getPluginId(),
                    descriptor.getProvider(),
                    descriptor.getVersion(),
                    pluginWrapper.getPluginState().toString(),
                    descriptor.getPluginDescription()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<SaturdayPlugin> getPlugins() {
        List<SaturdayPlugin> result = new ArrayList<>();
        pluginManager.getPlugins().forEach((pluginWrapper) -> {
            Plugin plugin = pluginWrapper.getPlugin();
            if (plugin instanceof SaturdayPlugin) {
                result.add((SaturdayPlugin) plugin);
            }
        });

        return result;
    }

}

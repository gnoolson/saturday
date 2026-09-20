package gnoolson.saturday.app.plugin;


import gnoolson.saturday_plugin_api.FunctionalityFactoryProvider;
import gnoolson.saturday_plugin_api.LuaModuleFactoryProvider;
import gnoolson.saturday_plugin_api.SaturdayPlugin;
import gnoolson.saturday_plugin_api.WebEndpoint;

import java.util.List;
import java.util.Optional;

public interface SaturdayPluginManager {

    Optional<String> getPluginId(Class<?> clazz);

    Optional<FunctionalityFactoryProvider> getFunctionalityFactoryProviders(String pluginId);

    Optional<LuaModuleFactoryProvider> getLuaModuleFactoryProviders(String pluginId);

    Optional<WebEndpoint> getWebEndpoint(String pluginId);

    void enablePlugin(String pluginId);

    void disablePlugin(String pluginId);

    boolean pluginExists(String pluginId);

    List<PluginInfo> getPluginInfos();

    List<SaturdayPlugin> getPlugins();

}

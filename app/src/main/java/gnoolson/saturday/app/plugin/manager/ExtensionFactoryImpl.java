package gnoolson.saturday.app.plugin.manager;

import gnoolson.saturday_plugin_api.SaturdayExtension;
import gnoolson.saturday_plugin_api.SaturdayPlugin;
import lombok.RequiredArgsConstructor;
import org.pf4j.DefaultExtensionFactory;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;

@RequiredArgsConstructor
public class ExtensionFactoryImpl extends DefaultExtensionFactory {

    private final PluginManager pluginManager;

    /*
     *
     *
     * */
    @Override
    public <T> T create(Class<T> extensionClass) {
        T instance = super.create(extensionClass);

        PluginWrapper pluginWrapper = pluginManager.whichPlugin(extensionClass);
        SaturdayPlugin plugin = (SaturdayPlugin) pluginWrapper.getPlugin();

        if (instance instanceof SaturdayExtension) {
            ((SaturdayExtension) instance).setup(plugin);
        }

        return instance;
    }
}

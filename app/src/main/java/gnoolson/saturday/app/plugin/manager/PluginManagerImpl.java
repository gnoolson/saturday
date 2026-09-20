package gnoolson.saturday.app.plugin.manager;

import gnoolson.saturday.lua_script_executor.ScriptLauncherGateway;
import org.pf4j.*;

import java.io.File;

public class PluginManagerImpl extends DefaultPluginManager {

    private final ScriptLauncherGateway scriptLauncherGateway;

    /*
     *
     *
     * */
    public PluginManagerImpl(ScriptLauncherGateway scriptLauncherGateway, File pluginsFolder) {
        super(pluginsFolder.toPath());
        this.scriptLauncherGateway = scriptLauncherGateway;
    }

    @Override
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return new PropertiesPluginDescriptorFinder("pf4j.properties");
    }

    @Override
    protected PluginStatusProvider createPluginStatusProvider() {
        return new PersistentPluginStatusProvider(getPluginsRoot().toFile());
    }

    public void enablePluginPersistent(String pluginId) {
        pluginStatusProvider.enablePlugin(pluginId);
        startPlugin(pluginId);
    }

    public void disablePluginPersistent(String pluginId) {
        stopPlugin(pluginId);
        pluginStatusProvider.disablePlugin(pluginId);
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new ExtensionFactoryImpl(this);
    }

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository().add(new RecursivePluginRepository(getPluginsRoot()), this::isNotDevelopment);
    }

    @Override
    protected PluginFactory createPluginFactory() {
        return new PluginFactoryImpl(() -> {
            return scriptLauncherGateway;
        });
    }

}

package gnoolson.saturday.app.plugin;

import gnoolson.saturday.app.plugin.manager.PluginManagerImpl;
import gnoolson.saturday.lua_script_executor.ScriptLauncherGateway;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class PluginLoader {

    public static SaturdayPluginManager load(String path,
                                             ScriptLauncherGateway scriptLauncherGateway
    ) throws FileNotFoundException {

        File folder = ResourceUtils.getFile(path);

        PluginManagerImpl pluginManager = new PluginManagerImpl(scriptLauncherGateway, folder);
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        return new SaturdayPluginManagerImpl(pluginManager);
    }

}

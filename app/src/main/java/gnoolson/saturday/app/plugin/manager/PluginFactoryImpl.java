package gnoolson.saturday.app.plugin.manager;

import gnoolson.saturday.lua_script_executor.ScriptLauncherGateway;
import gnoolson.saturday_plugin_api.SaturdayPlugin;
import lombok.RequiredArgsConstructor;
import org.pf4j.DefaultPluginFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class PluginFactoryImpl extends DefaultPluginFactory {

    private final Supplier<ScriptLauncherGateway> gatewaySupplier;

    /*
     *
     *
     * */
    @Override
    protected Plugin createInstance(Class<?> pluginClass, PluginWrapper pluginWrapper) {
        SaturdayPlugin plugin = (SaturdayPlugin) super.createInstance(pluginClass, pluginWrapper);
        plugin.setScriptLauncherGateway(new gnoolson.saturday_plugin_api.ScriptLauncherGateway() {
            @Override
            public void execute(String scriptId, Map<String, Object> map) {
                ScriptLauncherGateway scriptLauncherGateway = gatewaySupplier.get();
                scriptLauncherGateway.execute(scriptId, pluginWrapper.getPluginId(), map);
            }
        });

        return plugin;
    }

}

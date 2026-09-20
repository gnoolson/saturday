package gnoolson.saturday.app.config;

import gnoolson.saturday.app.plugin.PluginService;
import gnoolson.saturday.app.plugin.SaturdayPluginManager;
import gnoolson.saturday.broker.port.inbount.RestorePreviousStateUseCase;
import gnoolson.saturday.export_import.port.inbound.ImportDemoProjectsUseCase;
import gnoolson.saturday.script.port.inbound.AutostartScriptsUseCase;
import gnoolson.saturday_plugin_api.SaturdayPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupListener {

    @Autowired
    private AutostartScriptsUseCase autostartScriptsUseCase;
    @Autowired
    private SaturdayPluginManager pluginManager;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private RestorePreviousStateUseCase restorePreviousStateUseCase;
    @Autowired
    private ImportDemoProjectsUseCase importDemoProjectsUseCase;

    /*
     *
     *
     * */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        importDemoProjectsUseCase.execute();
        loadPlugins();
        startScripts();

        restorePreviousStateUseCase.execute();
    }

    /*
     *
     *
     * */
    private void startScripts() {
        autostartScriptsUseCase.execute();
    }

    private void loadPlugins() {
        pluginService.loadPlugins();

        List<SaturdayPlugin> plugins = pluginManager.getPlugins();
        for (SaturdayPlugin plugin : plugins) {
            plugin.onApplicationReady();
        }
    }

}

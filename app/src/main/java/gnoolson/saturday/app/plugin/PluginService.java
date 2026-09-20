package gnoolson.saturday.app.plugin;


import java.util.List;

public interface PluginService {

    void loadPlugins();

    List<PluginInfo> getPlugins();

    void control(String pluginId, boolean flag);

}

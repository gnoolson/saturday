package gnoolson.saturday.app.script.lib;

import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.lua_script_executor.lib.FunctionalityFactory;

import java.util.Collection;

public interface LuaLibFunctionalityProvider {

    Collection<Functionality> getFunctionalities();

    void addFactory(FunctionalityFactory functionalityFactory);

    void remove(String pluginId);

    void addFactory(String pluginId, gnoolson.saturday_plugin_api.FunctionalityFactory functionalityFactory);

}

package gnoolson.saturday.app.script.lib;

import gnoolson.saturday.lua_script_executor.Output;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.lua_script_executor.lib.FunctionalityFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LuaLibFunctionalityProviderImpl implements LuaLibFunctionalityProvider {

    private final List<FunctionalityFactory> luaLibFunctionalityFactories = new CopyOnWriteArrayList<>();

    /*
     *
     *
     * */
    @Override
    public Collection<Functionality> getFunctionalities() {
        Collection<Functionality> instances = new ArrayList<>(luaLibFunctionalityFactories.size());
        for (FunctionalityFactory functionalityFactory : luaLibFunctionalityFactories) {
            instances.add(functionalityFactory.getInstance());
        }
        return instances;
    }

    @Override
    public void addFactory(FunctionalityFactory newFunctionalityFactory) {
        for (FunctionalityFactory functionalityFactory : luaLibFunctionalityFactories) {
            if (functionalityFactory.getLuaLibId().equals(newFunctionalityFactory.getLuaLibId()))
                throw new RuntimeException(String.format("Duplicate FunctionalityFactory was found: \"%s\"", newFunctionalityFactory.getLuaLibId())); // +
        }

        luaLibFunctionalityFactories.add(newFunctionalityFactory);
    }

    @Override
    public void remove(String pluginId) {
        luaLibFunctionalityFactories.removeIf(FunctionalityFactory -> {
            return FunctionalityFactory.getLuaLibId().equals(Prefix.VALUE + pluginId);
        });
    }

    @Override
    public void addFactory(String pluginId, gnoolson.saturday_plugin_api.FunctionalityFactory functionalityFactory) {

        addFactory(new FunctionalityFactory() {
            @Override
            public String getLuaLibId() {
                return Prefix.VALUE + pluginId;
            }

            @Override
            public Functionality getInstance() {
                gnoolson.saturday_plugin_api.Functionality instance = functionalityFactory.getInstance();

                return new Functionality() {
                    @Override
                    public String getLuaLibId() {
                        return Prefix.VALUE + pluginId;
                    }

                    @Override
                    public void writeResultToOutput(Output output) {
                        instance.writeResultToOutput(new gnoolson.saturday_plugin_api.Output() {
                            @Override
                            public void print(Object o) {
                                output.println(o.toString());
                            }
                        });
                    }

                    @Override
                    public Object getFunctionalityInstance() {
                        return instance;
                    }
                };
            }
        });
    }


}

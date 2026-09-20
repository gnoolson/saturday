package gnoolson.saturday.internal_lua_libs.client;

import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.lua_script_executor.lib.FunctionalityFactory;

public class ClientFunctionalityFactory implements FunctionalityFactory {

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public Functionality getInstance() {
        return new ClientImpl();
    }

}

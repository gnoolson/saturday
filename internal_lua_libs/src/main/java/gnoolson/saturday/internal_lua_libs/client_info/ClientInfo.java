package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.lua_script_executor.lib.Functionality;

import java.util.List;

public interface ClientInfo extends Functionality {

    List<Client> get(ProjectId projectId);

    void setup(ClientsInProjectProviderGateway clientsInProjectProviderGateway);

    Client get(ClientId clientId);

}

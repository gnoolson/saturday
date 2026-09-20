package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.lua_script_executor.Output;

import java.util.List;

public class ClientInfoImpl implements ClientInfo {

    private ClientsInProjectProviderGateway clientsInProjectProviderGateway;
    private ClientProviderGateway clientProviderGateway;

    /*
     *
     *
     * */
    @Override
    public List<Client> get(ProjectId projectId) {
        return clientsInProjectProviderGateway.execute(projectId);
    }

    @Override
    public Client get(ClientId clientId) {
        return clientProviderGateway.execute(clientId);
    }

    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public void writeResultToOutput(Output output) {

    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

    @Override
    public void setup(ClientsInProjectProviderGateway clientsInProjectProviderGateway) {
        this.clientsInProjectProviderGateway = clientsInProjectProviderGateway;
    }


}

package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.model.vo.ProjectId;

import java.util.List;

public interface ClientsInProjectProviderGateway {

    List<Client> execute(ProjectId projectId);

}

package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.model.vo.ClientId;

public interface ClientProviderGateway {

    Client execute(ClientId clientId);

}

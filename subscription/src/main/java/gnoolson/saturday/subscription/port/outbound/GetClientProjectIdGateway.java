package gnoolson.saturday.subscription.port.outbound;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ProjectId;


public interface GetClientProjectIdGateway {

    ProjectId execute(ClientId clientId);

}

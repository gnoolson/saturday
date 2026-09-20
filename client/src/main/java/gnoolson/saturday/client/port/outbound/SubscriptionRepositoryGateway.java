package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.client.model.vo.Subscription;
import gnoolson.saturday.common.model.vo.ClientId;

import java.util.List;

public interface SubscriptionRepositoryGateway {

    List<Subscription> findByClientId(ClientId clientId);

}

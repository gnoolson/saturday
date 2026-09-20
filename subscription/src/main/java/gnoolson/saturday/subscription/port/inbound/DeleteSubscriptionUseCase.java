package gnoolson.saturday.subscription.port.inbound;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.SubscriptionId;


public interface DeleteSubscriptionUseCase {

    boolean execute(ClientId clientId, SubscriptionId subscriptionId);

}

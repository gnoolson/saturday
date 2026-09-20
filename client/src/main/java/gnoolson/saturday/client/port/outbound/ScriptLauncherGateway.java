package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.client.model.vo.IncomingMessage;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.SubscriptionId;


public interface ScriptLauncherGateway {

    void execute(ScriptId scriptId, ClientId clientId, SubscriptionId subscriptionId, IncomingMessage incomingMessage);

}

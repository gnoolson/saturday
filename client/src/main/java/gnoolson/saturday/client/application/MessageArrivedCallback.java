package gnoolson.saturday.client.application;

import gnoolson.saturday.client.model.vo.IncomingMessage;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.SubscriptionId;


public interface MessageArrivedCallback {

    void execute(SubscriptionId subscriptionId, ScriptId scriptId, IncomingMessage incomingMessage);

}

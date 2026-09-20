package gnoolson.saturday.subscription.model.exception;

import gnoolson.saturday.common.model.vo.SubscriptionId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscriptionNotFoundException extends RuntimeException {

    private final SubscriptionId subscriptionId;

    @Override
    public String getMessage() {
        return String.format("Subscription \"%s\" was not found", subscriptionId.getValue().toString());
    }

}

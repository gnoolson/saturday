package gnoolson.saturday.broker.model;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MQTTBrokerState {

    private final PositiveNumber port;
    private final boolean allowAnonymousConnections;
    private final boolean started;

}

package gnoolson.saturday.broker.port.inbount;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface GetMQTTBrokerUseCase {

    BrokerDto execute();

    @RequiredArgsConstructor
    @Getter
    class BrokerDto {
        private final boolean started;
        private final boolean allowAnonymousConnections;
        private final PositiveNumber port;
    }


}

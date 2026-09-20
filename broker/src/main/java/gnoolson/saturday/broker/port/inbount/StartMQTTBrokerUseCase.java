package gnoolson.saturday.broker.port.inbount;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface StartMQTTBrokerUseCase {

    void execute(StartMQTTBrokerDto broker);

    @RequiredArgsConstructor
    @Getter
    class StartMQTTBrokerDto {
        private final boolean allowAnonymousConnections;
        private final PositiveNumber port;
    }

}

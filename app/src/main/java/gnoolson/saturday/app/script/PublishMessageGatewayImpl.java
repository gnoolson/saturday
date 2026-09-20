package gnoolson.saturday.app.script;

import gnoolson.saturday.client.port.inbound.PublishMessageUseCase;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.internal_lua_libs.client.PublishMessageGateway;
import gnoolson.saturday.script.model.vo.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PublishMessageGatewayImpl implements PublishMessageGateway {

    private final PublishMessageUseCase publishMessageUseCase;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ClientId clientId, Message message) {
        return publishMessageUseCase.execute(clientId, message.getTopic(), message.getPayload(), message.getQos());
    }

}

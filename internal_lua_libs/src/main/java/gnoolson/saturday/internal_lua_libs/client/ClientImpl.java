package gnoolson.saturday.internal_lua_libs.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import gnoolson.saturday.common.HexFormat;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.Payload;
import gnoolson.saturday.lua_script_executor.Output;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientImpl implements Client {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private PublishMessageGateway publishMessageGateway;
    private ClientId id;

    private IncomingMessage incomingMessage;
    private Output output;
    private boolean resultToOutput;

    /*
     *
     *
     * */
    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public boolean isIncomingMessageAvailable() {
        return incomingMessage != null;
    }

    @Override
    public boolean send(OutgoingMessage outgoingMessage) {
        if (resultToOutput) {
            Map<String, Object> data = new HashMap<>();
            data.put("topic", outgoingMessage.getTopic().getValue());
            data.put("qos", outgoingMessage.getQos().getValue());
            data.put("payload", HexFormat.bytesToString(outgoingMessage.getPayload()));

            if (output == null)
                throw new IllegalStateException("Output is not initialized"); // +

            output.println("-- Client.send()");
            try {
                String result = JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                output.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            output.println();
            return true;
        } else {
            if (id == null)
                throw new IllegalStateException("ClientId is not initialized"); // +

            return publishMessageGateway.execute(id, new gnoolson.saturday.script.model.vo.Message(outgoingMessage.getTopic(), Payload.of(outgoingMessage.getPayload()), outgoingMessage.getQos()));
        }
    }

    @Override
    public Optional<IncomingMessage> getIncomingMessage() {
        return Optional.ofNullable(incomingMessage);
    }

    @Override
    public void setup(ClientId clientId, PublishMessageGateway publishMessageGateway, IncomingMessage incomingMessage) {
        this.id = clientId;
        this.incomingMessage = incomingMessage;
        this.publishMessageGateway = publishMessageGateway;
    }

    @Override
    public void setup(PublishMessageGateway publishMessageGateway) {
        this.publishMessageGateway = publishMessageGateway;
    }

    @Override
    public void useOutput(boolean flag) {
        this.resultToOutput = flag;
    }

    @Override
    public Optional<ClientId> getClientId() {
        return Optional.ofNullable(id);
    }

    @Override
    public void setClientId(ClientId clientId) {
        this.id = clientId;
    }

    @Override
    public void writeResultToOutput(Output output) {
        this.output = output;
    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

}

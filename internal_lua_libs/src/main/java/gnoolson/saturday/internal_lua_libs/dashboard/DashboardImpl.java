package gnoolson.saturday.internal_lua_libs.dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.lua_script_executor.Output;

import java.util.Optional;

public class DashboardImpl implements Dashboard {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private SendDashboardDataGateway sendDashboardDataGateway;
    private IncomingMessage incomingMessage;
    private Output output;
    private boolean resultToOutput;
    private OpenDashboardId openDashboardId;

    /*
     *
     *
     * */
    @Override
    public String getLuaLibId() {
        return Id.VALUE;
    }

    @Override
    public boolean isMessageAvailable() {
        return incomingMessage != null;
    }

    @Override
    public Optional<IncomingMessage> getMessage() {
        return Optional.ofNullable(incomingMessage);
    }

    @Override
    public void sendMessage(OutgoingMessage outgoingMessage) {
        if (resultToOutput) {
            if (output == null)
                throw new IllegalStateException("Output is not initialized"); // +

            output.println("-- Dashboard.sendMessage()");
            try {
                String result = JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(outgoingMessage.getData());
                output.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            output.println();
        } else {
            sendDashboardDataGateway.execute(openDashboardId, outgoingMessage);
        }
    }

    @Override
    public Optional<OpenDashboardId> getOpenDashboardId() {
        return Optional.ofNullable(openDashboardId);
    }


    @Override
    public void writeResultToOutput(Output output) {
        resultToOutput = true;
        this.output = output;
    }

    @Override
    public Object getFunctionalityInstance() {
        return this;
    }

    @Override
    public void setup(SendDashboardDataGateway sendDashboardDataGateway) {
        this.sendDashboardDataGateway = sendDashboardDataGateway;
    }

    @Override
    public void setup(SendDashboardDataGateway sendDashboardDataGateway, OpenDashboardId openDashboardId, IncomingMessage incomingMessage) {
        this.sendDashboardDataGateway = sendDashboardDataGateway;
        this.openDashboardId = openDashboardId;
        this.incomingMessage = incomingMessage;
    }

}

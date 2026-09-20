package gnoolson.saturday.app.web.script.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gnoolson.saturday.app.script.Dev;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.common.HexFormat;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.script.model.exception.TestDataException;
import gnoolson.saturday.script.model.vo.Code;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class DevRequestDtoMapper {


    public static Dev.RequestDto toDomainDto(JsonNode requestBody) {
        try {
            ProjectId projectId = getProjectId(requestBody);
            Code code = getCode(requestBody);
            Collection<ScriptId> includedScripts = getScriptIds(requestBody);
            ScriptId scriptId = getScriptId(requestBody);
            boolean writeLogToOutput = isLogToOutput(requestBody);
            boolean outgoingMessageWriteToOutput = isOutgoingMessageToOutput(requestBody);
            boolean logDebugEnabled = isLogDebugEnabled(requestBody);

            JsonNode mockData = getMockData(requestBody); // +
            Dev.ClientDto clientDto = getMessageBoxDto(mockData); //+
            Dev.DashboardMessageDto dashboardMessageDto = getDashboardMessageDto(mockData); // +
            Dev.ArgsDto argsDto = getArgsDto(mockData); // +

            return new Dev.RequestDto(
                    projectId,
                    scriptId,
                    code,
                    includedScripts,
                    clientDto,
                    dashboardMessageDto,
                    argsDto,
                    writeLogToOutput,
                    outgoingMessageWriteToOutput,
                    logDebugEnabled
            );
        } catch (Exception e) {
            throw new TestDataException(e); // +
        }
    }

    /*
     *
     *
     * */
    private static ProjectId getProjectId(JsonNode requestBody) {
        String string = requestBody.get("projectId").asText();
        DomainModelValidator.checkNotNull(string, "request.projectId"); // +

        return ProjectId.of(UUID.fromString(string));
    }

    private static Dev.DashboardMessageDto getDashboardMessageDto(JsonNode mockData) {
        JsonNode dashboardJSON = mockData.get("dashboard");
        DomainModelValidator.checkNotNull(dashboardJSON, "request.mockData.dashboard"); //+

        if (!dashboardJSON.has("incomingMessage")) {
            return new Dev.DashboardMessageDto();
        }

        Object incomingMessageData = dashboardJSON.get("incomingMessage");
        DomainModelValidator.checkNotNull(incomingMessageData, "request.mockData.dashboard.incomingMessage"); //+

        String dashboardIdStr = dashboardJSON.get("dashboardId").asText();
        DomainModelValidator.checkNotNull(dashboardIdStr, "request.mockData.dashboard.dashboardId"); //+

        String openDashboardIdStr = dashboardJSON.get("openDashboardId").asText();
        DomainModelValidator.checkNotNull(openDashboardIdStr, "request.mockData.dashboard.openDashboardId"); //+

        return new Dev.DashboardMessageDto(UUID.fromString(dashboardIdStr), UUID.fromString(openDashboardIdStr), incomingMessageData);
    }

    private static Dev.ArgsDto getArgsDto(JsonNode mockData) {
        JsonNode argsJSON = mockData.get("args");
        DomainModelValidator.checkNotNull(argsJSON, "request.mockData.args");

        return new Dev.ArgsDto(JSON.convert(argsJSON, new TypeReference<Map<String, Object>>() {
        }));
    }

    private static boolean isOutgoingMessageToOutput(JsonNode requestBody) {
        boolean result = requestBody.get("outgoingMessageToOutput").asBoolean();
        return result;
    }

    private static boolean isLogToOutput(JsonNode requestBody) {
        boolean result = requestBody.get("logToOutput").asBoolean();
        return result;
    }

    private static boolean isLogDebugEnabled(JsonNode requestBody) {
        boolean result = requestBody.get("logDebugEnabled").asBoolean();
        return result;
    }

    private static ScriptId getScriptId(JsonNode requestBody) {
        String scriptIdValue = requestBody.get("scriptId").asText();
        DomainModelValidator.checkNotNull(scriptIdValue, "request.scriptId");
        ScriptId scriptId = ScriptId.of(UUID.fromString(scriptIdValue));
        return scriptId;
    }

    private static Dev.ClientDto getMessageBoxDto(JsonNode mockData) {
        JsonNode clientJSON = mockData.get("client");
        DomainModelValidator.checkNotNull(clientJSON, "request.mockData.client");

        if (!clientJSON.has("incomingMessage")) {
            return new Dev.ClientDto();
        }

        JsonNode incomingMessageJSON = clientJSON.get("incomingMessage");
        DomainModelValidator.checkNotNull(incomingMessageJSON, "request.mockData.client.incomingMessage");

        String payload = incomingMessageJSON.get("payload").asText();
        DomainModelValidator.checkNotNull(payload, "request.mockData.client.incomingMessage.payload");

        byte[] bytes = HexFormat.stringToByteArray(payload);

        Dev.ClientDto clientDto = new Dev.ClientDto(
                ClientId.of(UUID.fromString(clientJSON.get("clientId").asText())),
                TopicFilter.of(incomingMessageJSON.get("topicFilter").asText()),
                Topic.of(incomingMessageJSON.get("topic").asText()),
                QoS.of(incomingMessageJSON.get("qos").asInt()),
                Payload.of(bytes)
        );
        return clientDto;
    }

    private static JsonNode getMockData(JsonNode requestBody) {
        JsonNode mockData = requestBody.get("mockData");
        DomainModelValidator.checkNotNull(mockData, "request.mockData");
        return mockData;
    }

    private static Collection<ScriptId> getScriptIds(JsonNode requestBody) {
        JsonNode jsonArray = requestBody.get("includedScripts");
        DomainModelValidator.checkNotNull(jsonArray, "request.includedScripts");

        if(!jsonArray.isArray())
            throw new RuntimeException("Array \"includedScripts\" was not found");

        ArrayNode arrayNode = (ArrayNode) jsonArray;
        Stream<JsonNode> stream = StreamSupport.stream(arrayNode.spliterator(), false);
        Set<ScriptId> includedScripts = stream.map(jsonNode -> {
            String id = jsonNode.asText();
            return ScriptId.of(UUID.fromString(id));
        }).collect(Collectors.toSet());

        if (includedScripts.contains(getScriptId(requestBody)))
            throw new IllegalArgumentException("Script cannot include itself");  // +

        return includedScripts;
    }

    private static Code getCode(JsonNode requestBody) {
        String codeValue = requestBody.get("code").asText();
        DomainModelValidator.checkNotNull(codeValue, "request.code");
        Code code = Code.of(codeValue);
        return code;
    }

    public static ObjectNode toDto(Dev.ResponseDto responseDto) {
        ObjectNode response = JSON.createObjectNode();
        response.put("executionTime", responseDto.getExecutionTime().getValue());
        response.put("output", responseDto.getOutput().toString());

        return response;
    }

}

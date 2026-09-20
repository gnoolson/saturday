package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.lua_script_executor.Output;
import gnoolson.saturday.script.model.vo.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface Dev {

    ResponseDto execute(RequestDto requestDto);

    @Getter
    @RequiredArgsConstructor
    class RequestDto {
        private final ProjectId projectId;
        private final ScriptId id;
        private final Code code;
        private final Collection<ScriptId> includedScripts;
        private final ClientDto clientDto;
        private final DashboardMessageDto dashboard;
        private final ArgsDto args;
        private final boolean writeLogToOutput;
        private final boolean writeOutgoingMessageToOutput;
        private final boolean logDebugEnabled;
    }

    @Getter
    @RequiredArgsConstructor
    class ResponseDto {
        private final Output output;
        private final TimeInMs executionTime;
    }

    @Getter
    class ClientDto {
        private final ClientId clientId;
        private final Topic topic;
        private final TopicFilter topicFilter;
        private final QoS qos;
        private final Payload payload;
        private final boolean empty;

        public ClientDto(ClientId clientId, TopicFilter topicFilter, Topic topic, QoS qos, Payload payload) {
            this.clientId = clientId;
            this.topic = topic;
            this.topicFilter = topicFilter;
            this.qos = qos;
            this.payload = payload;
            this.empty = false;
        }

        public ClientDto() {
            this.empty = true;
            this.clientId = ClientId.empty();
            this.topic = Topic.of("EMPTY");
            this.topicFilter = TopicFilter.of("EMPTY");
            this.qos = QoS.of(0);
            this.payload = Payload.of(new byte[0]);
        }
    }

    @Getter
    class DashboardMessageDto {
        private final Object data;
        private final UUID dashboardId;
        private final UUID openDashboardId;
        private final boolean empty;

        public DashboardMessageDto(UUID dashboardId, UUID openDashboardId, Object data) {
            this.empty = false;
            this.data = data;
            this.openDashboardId = openDashboardId;
            this.dashboardId = dashboardId;
        }

        public DashboardMessageDto() {
            this.empty = true;
            this.data = new Object();
            this.openDashboardId = UUID.randomUUID();
            this.dashboardId = UUID.randomUUID();
        }
    }

    @Getter
    class ArgsDto {
        private final Map<String, Object> data;

        public ArgsDto() {
            this.data = new HashMap<>(0);
        }

        public ArgsDto(Map<String, Object> data) {
            this.data = data;
        }
    }

}

package gnoolson.saturday.app.web.subscription.dto;

import gnoolson.saturday.app.web.dto.NotEmptyUUIDValidation;
import gnoolson.saturday.app.web.dto.Regexes;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.subscription.port.inbound.GetAllSubscriptionsUseCase;
import gnoolson.saturday.subscription.port.inbound.GetSubscriptionUseCase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.UUID;

@Setter
@NoArgsConstructor
@Getter
public class SubscriptionDto {

    private UUID id;

    @NotNull
    @NotEmptyUUIDValidation(message = "{error.client_id}")
    private UUID clientId;

    @NotNull
    @NotEmptyUUIDValidation(message = "{error.script_id}")
    private UUID scriptId;

    @NotBlank
    @Pattern(regexp = Regexes.TOPIC_FILTER_FOR_SUBSCRIPTION, message = "{error.subscription.topic_filter}")
    private String topicFilter;

    @Length(max = 5000)
    private String description;

    @Min(0)
    @Max(2)
    private int qos;

    /*
     *
     *
     * */
    public SubscriptionDto(GetAllSubscriptionsUseCase.SubscriptionDto subscriptionDto) {
        this.id = subscriptionDto.getId().getValue();
        this.clientId = subscriptionDto.getClientId().getValue();
        this.scriptId = subscriptionDto.getScriptId().getValue();
        this.topicFilter = subscriptionDto.getTopicFilter().getValue();
        this.description = subscriptionDto.getDescription().getValue();
        this.qos = subscriptionDto.getQos().getValue();
    }

    public SubscriptionDto(ClientId clientId) {
        this.id = SubscriptionId.empty().getValue();
        this.clientId = clientId.getValue();
        this.topicFilter = StringUtils.EMPTY;
        this.scriptId = ScriptId.empty().getValue();
        this.description = StringUtils.EMPTY;
        this.qos = 1;
    }

    public SubscriptionDto(GetSubscriptionUseCase.SubscriptionDto subscriptionDto) {
        this.id = subscriptionDto.getId().getValue();
        this.clientId = subscriptionDto.getClientId().getValue();
        this.scriptId = subscriptionDto.getScriptId().getValue();
        this.topicFilter = subscriptionDto.getTopicFilter().getValue();
        this.description = subscriptionDto.getDescription().getValue();
        this.qos = subscriptionDto.getQos().getValue();
    }

}

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
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Setter
@NoArgsConstructor
@Getter
public class SubscriptionDto {

    private UUID id;

    @NonNull
    @NotEmptyUUIDValidation(message = "{subscription.upsert.form.client_id.error}")
    private UUID clientId;

    @NonNull
    @NotEmptyUUIDValidation(message = "{subscription.upsert.form.script_id.error}")
    private UUID scriptId;

    @NotBlank
    @Pattern(regexp = Regexes.TOPIC_FILTER_FOR_SUBSCRIPTION, message = "{subscription.upsert.form.topic_filter.error}")
    private String topicFilter;

    @Length(max = 500)
    private String description;

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
    }

    public SubscriptionDto(ClientId clientId) {
        this.id = SubscriptionId.empty().getValue();
        this.clientId = clientId.getValue();
        this.topicFilter = StringUtils.EMPTY;
        this.scriptId = ScriptId.empty().getValue();
        this.description = StringUtils.EMPTY;
    }

    public SubscriptionDto(GetSubscriptionUseCase.SubscriptionDto subscriptionDto) {
        this.id = subscriptionDto.getId().getValue();
        this.clientId = subscriptionDto.getClientId().getValue();
        this.scriptId = subscriptionDto.getScriptId().getValue();
        this.topicFilter = subscriptionDto.getTopicFilter().getValue();
        this.description = subscriptionDto.getDescription().getValue();
    }

}

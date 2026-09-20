package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class SubscriptionId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private SubscriptionId() {
        this.value = EmptyId.getValue();
    }

    public SubscriptionId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "SubscriptionId"); // +
        this.value = value;
    }

    public static SubscriptionId of(UUID value) {
        return new SubscriptionId(value);
    }

    public static SubscriptionId empty() {
        return new SubscriptionId();
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "subscription_id:" + value.toString();
    }

}

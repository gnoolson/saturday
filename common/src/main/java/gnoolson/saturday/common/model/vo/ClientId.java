package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class ClientId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private ClientId() {
        this.value = EmptyId.getValue();
    }

    public ClientId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "ClientId"); // +
        this.value = value;
    }

    public static ClientId of(UUID value) {
        return new ClientId(value);
    }

    public static ClientId random() {
        return new ClientId(UUID.randomUUID());
    }

    public static ClientId empty() {
        return new ClientId();
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "client_id:" + value.toString();
    }

}

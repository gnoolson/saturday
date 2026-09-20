package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Payload {

    private final byte[] value;

    /*
     *
     *
     * */
    public Payload(byte[] value) {
        ValueObjectValidator.checkNotNull(value, "Payload"); // +
        this.value = value;
    }

    public static Payload of(byte[] value) {
        return new Payload(value);
    }

}

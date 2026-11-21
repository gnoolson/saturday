package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Base64 {

    private final String value;

    /*
     *
     *
     * */
    public Base64(String value) {
        ValueObjectValidator.checkNotNull(value, "Base64 value"); // +
        this.value = value;
    }

    public static Base64 of(String value) {
        return new Base64(value);
    }

}

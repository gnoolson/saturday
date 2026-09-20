package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Username {

    private final String value;

    /*
     *
     *
     * */
    public Username(final String value) {
        ValueObjectValidator.checkNotNull(value, "Username"); // +
        this.value = value.trim();
    }

    public static Username of(String value) {
        return new Username(value);
    }

}

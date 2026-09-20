package gnoolson.saturday.user.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Password {

    private final String value;

    /*
     *
     *
     * */
    public Password(String value) {
        ValueObjectValidator.checkNotNull(value, "Password");
        this.value = value.trim();
    }

    public static Password of(String value) {
        return new Password(value);
    }

}

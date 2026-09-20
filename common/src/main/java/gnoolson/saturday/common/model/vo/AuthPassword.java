package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class AuthPassword {

    private final String value;

    /*
     *
     *
     * */
    private AuthPassword() {
        this.value = "";
    }

    public AuthPassword(String value) {
        ValueObjectValidator.checkNotNull(value, "AuthPassword"); // +
        this.value = value;
    }

    public static AuthPassword of(String value) {
        return new AuthPassword(value);
    }

    public static AuthPassword empty() {
        return new AuthPassword();
    }

}

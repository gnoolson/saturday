package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class AuthUsername implements Id {

    private final String value;

    /*
     *
     *
     * */
    private AuthUsername() {
        this.value = "";
    }

    public AuthUsername(String value) {
        ValueObjectValidator.checkNotNull(value, "AuthUsername"); // +
        this.value = value;
    }

    public static AuthUsername of(String value) {
        return new AuthUsername(value);
    }

    public static AuthUsername empty() {
        return new AuthUsername();
    }

    @Override
    public String getStringValue() {
        return "auth_username:" + value;
    }

}

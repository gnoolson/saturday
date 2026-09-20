package gnoolson.saturday.client.model.vo;

import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class ClientAuth {

    private final boolean use;
    private final AuthUsername username;
    private final AuthPassword password;

    /*
     *
     *
     * */
    public ClientAuth(boolean use, AuthUsername username, AuthPassword password) {
        DomainModelValidator.checkNotNull(username, "AuthUsername"); // +
        DomainModelValidator.checkNotNull(password, "AuthPassword"); // +

        this.use = use;
        this.username = username;
        this.password = password;
    }

    public static ClientAuth of(boolean use, AuthUsername username, AuthPassword password) {
        return new ClientAuth(use, username, password);
    }

}

package gnoolson.saturday.user.model.entity;

import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.user.model.vo.Password;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class User {

    private final Username username;
    private Password password;
    private Role role;

    /*
     *
     *
     * */
    public User(Username username, Password password, Role role) {
        DomainModelValidator.checkNotNull(username, "Username");
        DomainModelValidator.checkNotNull(password, "Password");
        DomainModelValidator.checkNotNull(role, "Role");

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setRole(Role role) {
        DomainModelValidator.checkNotNull(role, "Role");
        this.role = role;
    }

    public void setPassword(Password password) {
        DomainModelValidator.checkNotNull(password, "Password");
        this.password = password;
    }

}

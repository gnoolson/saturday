package gnoolson.saturday.app.web.user.dto;

import gnoolson.saturday.app.web.dto.Regexes;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class DeleteUserDto {

    @Pattern(regexp = Regexes.USER_AUTH_USERNAME, message = "{error.user.username}")
    private String username;

}

package gnoolson.saturday.app.web.create_super.dto;

import gnoolson.saturday.app.web.dto.Regexes;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class CreateSuperDto {

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_USERNAME, message = "{error.user.username}")
    private String username;

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_PASSWORD, message = "{error.user.password}")
    private String password;

}

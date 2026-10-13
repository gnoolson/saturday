package gnoolson.saturday.app.web.login.dto;

import gnoolson.saturday.app.web.dto.Regexes;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequestDto {

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_USERNAME, message = "{login.error_message.1}")
    private String username;

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_PASSWORD, message = "{login.error_message.2}")
    private String password;

}

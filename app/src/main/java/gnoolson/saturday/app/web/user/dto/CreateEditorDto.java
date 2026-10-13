package gnoolson.saturday.app.web.user.dto;

import gnoolson.saturday.app.web.dto.Regexes;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class CreateEditorDto {

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_USERNAME, message = "{user.all.error.username}")
    private String username;

    @NotBlank
    @Pattern(regexp = Regexes.USER_AUTH_PASSWORD, message = "{user.all.error.password}")
    private String password;

}

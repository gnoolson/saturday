package gnoolson.saturday.app.web.user.dto;

import gnoolson.saturday.app.web.dto.Regexes;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class DeleteUserDto {

    @Pattern(regexp = Regexes.USERNAME, message = "{user.all.error.username}")
    private String username;

}

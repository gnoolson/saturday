package gnoolson.saturday.app.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private final String username;
    private final String role;
    private final boolean you;

}

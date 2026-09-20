package gnoolson.saturday.app.web.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}

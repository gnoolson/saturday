package gnoolson.saturday.app.web.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeleteUserDto {

    @NotEmpty
    private String username;

}

package gnoolson.saturday.app.repository.user.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    private String username;
    private String password;
    private String role;

}

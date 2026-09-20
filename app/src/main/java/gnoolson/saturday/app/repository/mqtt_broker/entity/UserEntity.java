package gnoolson.saturday.app.repository.mqtt_broker.entity;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserEntity {

    private String username;
    private String password;

}

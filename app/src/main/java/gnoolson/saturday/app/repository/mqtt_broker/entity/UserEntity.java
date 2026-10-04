package gnoolson.saturday.app.repository.mqtt_broker.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserEntity implements Serializable {

    private String username;
    private String password;

}

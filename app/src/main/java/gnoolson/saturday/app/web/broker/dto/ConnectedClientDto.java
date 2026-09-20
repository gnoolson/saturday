package gnoolson.saturday.app.web.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ConnectedClientDto {

    private String clientName;
    private String username;
    private long connected;

}

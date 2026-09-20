package gnoolson.saturday.app.import_export.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientAuthDto {

    private boolean use;
    private String username;
    private String password;

}

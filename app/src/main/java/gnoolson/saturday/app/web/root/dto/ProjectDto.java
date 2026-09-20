package gnoolson.saturday.app.web.root.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProjectDto {

    private UUID id;
    private String name;

}

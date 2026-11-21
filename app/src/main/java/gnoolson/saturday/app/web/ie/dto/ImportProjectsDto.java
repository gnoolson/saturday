package gnoolson.saturday.app.web.ie.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ImportProjectsDto {

    @NotEmpty
    private Set<UUID> projectIdSet;

    @NotEmpty
    private String path;

}

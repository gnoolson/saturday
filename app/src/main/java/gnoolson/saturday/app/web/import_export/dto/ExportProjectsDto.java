package gnoolson.saturday.app.web.import_export.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ExportProjectsDto {

    @NotNull
    @NotEmpty
    private Set<UUID> projectIdSet;

}

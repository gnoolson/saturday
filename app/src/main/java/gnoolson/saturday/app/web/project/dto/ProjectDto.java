package gnoolson.saturday.app.web.project.dto;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.port.inbound.GetAllProjectsUseCase;
import gnoolson.saturday.project.port.inbound.GetProjectUseCase;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class ProjectDto {

    private UUID id;

    @NotBlank
    @Length(min = 1, max = 64)
    private String name;

    @Length(max = 5000)
    private String description;

    /*
     *
     *
     * */
    public ProjectDto(GetAllProjectsUseCase.ProjectDto projectDto) {
        this.id = projectDto.getProjectId().getValue();
        this.name = projectDto.getName().getValue();
        this.description = projectDto.getDescription().getValue();
    }

    public ProjectDto() {
        this.id = ProjectId.empty().getValue();
        this.name = ProjectName.empty().getValue();
        this.description = Description.empty().getValue();
    }

    public ProjectDto(GetProjectUseCase.ProjectDto projectDto) {
        this.id = projectDto.getProjectId().getValue();
        this.name = projectDto.getName().getValue();
        this.description = projectDto.getDescription().getValue();
    }

}

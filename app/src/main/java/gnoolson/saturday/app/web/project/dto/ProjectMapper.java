package gnoolson.saturday.app.web.project.dto;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.port.inbound.CreateProjectUseCase;
import gnoolson.saturday.project.port.inbound.GetAllProjectsUseCase;
import gnoolson.saturday.project.port.inbound.UpdateProjectUseCase;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Collection<ProjectDto> toDto(List<GetAllProjectsUseCase.ProjectDto> projects) {
        return projects.stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    public static CreateProjectUseCase.ProjectDto toDomainDtoForCreateUseCase(ProjectDto projectDto) {
        return new CreateProjectUseCase.ProjectDto(
                ProjectName.of(projectDto.getName()),
                Description.of(projectDto.getDescription())
        );
    }

    public static UpdateProjectUseCase.ProjectDto toDomainForUpdateUseCase(ProjectDto projectDto) {
        return new UpdateProjectUseCase.ProjectDto(
                ProjectId.of(projectDto.getId()),
                ProjectName.of(projectDto.getName()),
                Description.of(projectDto.getDescription())
        );
    }

}

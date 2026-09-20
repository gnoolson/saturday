package gnoolson.saturday.app.web.root.dto;

import gnoolson.saturday.project.port.inbound.GetAllProjectsUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectDtoMapper {

    public static List<ProjectDto> toDto(List<GetAllProjectsUseCase.ProjectDto> domainDtoList) {
        return domainDtoList.stream().map((domainDto) -> {
            return new ProjectDto(
                    domainDto.getProjectId().getValue(),
                    domainDto.getName().getValue()
            );
        }).collect(Collectors.toList());
    }

}

package gnoolson.saturday.project.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.inbound.GetProjectsForExportUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetProjectsForExportUseCaseImpl implements GetProjectsForExportUseCase {

    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ProjectDto> execute(Set<ProjectId> projectIdSet) {
        List<Project> projects = projectRepositoryGateway.findAll();


        return projects.stream()
                .filter(project -> {
                    return projectIdSet.contains(project.getId());
                }).map(project -> {
                    return new ProjectDto(
                            project.getId(),
                            project.getName(),
                            project.getDescription()
                    );
                }).collect(Collectors.toList());
    }

}

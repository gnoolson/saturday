package gnoolson.saturday.project.application;

import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.inbound.GetAllProjectsUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllProjectsUseCaseImpl implements GetAllProjectsUseCase {

    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public List<ProjectDto> execute() {
        List<Project> projects = projectRepositoryGateway.findAll();

        return projects.stream().map(entity -> {
            return new ProjectDto(entity.getId(), entity.getName(), entity.getDescription());
        }).collect(Collectors.toList());
    }

}

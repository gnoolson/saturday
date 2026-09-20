package gnoolson.saturday.project.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.model.exception.ProjectNotFoundException;
import gnoolson.saturday.project.port.inbound.GetProjectUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetProjectUseCaseImpl implements GetProjectUseCase {

    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public ProjectDto execute(ProjectId id) {
        Project project = projectRepositoryGateway.find(id).orElseThrow(() -> {
            return new ProjectNotFoundException(id);
        });


        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }

}

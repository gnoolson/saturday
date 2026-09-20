package gnoolson.saturday.project.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.inbound.ImportProjectsUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImportProjectsUseCaseImpl implements ImportProjectsUseCase {

    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(List<ProjectDto> projects) {
        for (ProjectDto project : projects) {
            save(project);
        }
    }

    /*
     *
     *
     * */
    private void save(ProjectDto projectDto) {
        checkId(projectDto.getId());
        checkName(projectDto.getName());

        Project project = new Project(
                projectDto.getId(),
                projectDto.getName(),
                projectDto.getDescription()
        );

        projectRepositoryGateway.save(project);

    }

    private void checkId(ProjectId id) {
        if (projectRepositoryGateway.exists(id))
            throw new RuntimeException(String.format("Project \"%s\" already exists", id.getValue().toString())); // +
    }

    private void checkName(ProjectName name) {
        if (projectRepositoryGateway.find(name).isPresent())
            throw new RuntimeException(String.format("Project \"%s\" already exists", name.getValue())); // +
    }

}

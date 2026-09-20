package gnoolson.saturday.project.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.model.exception.ProjectNotFoundException;
import gnoolson.saturday.project.port.inbound.UpdateProjectUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateProjectUseCaseImpl implements UpdateProjectUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(ProjectDto projectDto) {
        Project project = projectRepositoryGateway.find(projectDto.getId()).orElseThrow(() -> new ProjectNotFoundException(projectDto.getId()));

        try (Locker.LockHandle ignore = locker.lockIds(
                LockId.of(projectDto.getId()),
                LockId.of(projectDto.getName()),
                LockId.of(projectDto.getName()))) {

            checkName(project.getName(), projectDto.getName());

            project.update(projectDto.getName(), projectDto.getDescription());

            transactionStarter.doIt(() -> {
                projectRepositoryGateway.save(project);
            });
        }
    }

    /*
     *
     *
     * */
    private void checkName(ProjectName name, ProjectName newName) {
        if (name.equals(newName))
            return;

        if (projectRepositoryGateway.find(newName).isPresent())
            throw new RuntimeException(String.format("Project \"%s\" already exists", newName.toString())); // +
    }

}

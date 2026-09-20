package gnoolson.saturday.project.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.inbound.CreateProjectUseCase;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    private final TransactionStarter transactionStarter;
    private final Locker locker;
    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public ProjectId execute(ProjectDto projectDto) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(projectDto.getName()))) {

            checkName(projectDto.getName());

            Project project = new Project(
                    projectDto.getName(),
                    projectDto.getDescription()
            );

            AtomicReference<ProjectId> result = new AtomicReference<>();
            transactionStarter.doIt(() -> {
                ProjectId id = projectRepositoryGateway.save(project);
                result.set(id);
            });

            return result.get();
        }
    }

    /*
     *
     *
     * */
    private void checkName(ProjectName name) {
        if (projectRepositoryGateway.find(name).isPresent())
            throw new RuntimeException(String.format("Project \"%s\" already exists", name.toString())); // +
    }

}

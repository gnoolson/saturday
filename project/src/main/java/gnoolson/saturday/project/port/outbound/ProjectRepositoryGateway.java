package gnoolson.saturday.project.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryGateway {

    List<Project> findAll();

    Optional<Project> find(ProjectId id);

    ProjectId save(Project project);

    Optional<Object> find(ProjectName name);

    void delete(ProjectId id);

    boolean exists(ProjectId id);

}

package gnoolson.saturday.app.repository.project;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.project.entity.ProjectMapper;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryGatewayImpl implements ProjectRepositoryGateway {

    private final ProjectJPARepository projectJPARepository;

    /*
     *
     *
     * */
    @Override
    public List<Project> findAll() {
        Iterable<ProjectEntity> entities = projectJPARepository.findAll();

        List<Project> result = new ArrayList<>();
        for (ProjectEntity entity : entities) {
            Project project = ProjectMapper.toDomain(entity);
            result.add(project);
        }

        return result;
    }


    @Override
    public Optional<Project> find(ProjectId id) {
        Optional<ProjectEntity> entityOpt = projectJPARepository.findById(id.getValue());
        return entityOpt.map(ProjectMapper::toDomain);
    }


    @Override
    public Optional<Object> find(ProjectName name) {
        Optional<ProjectEntity> entityOpt = projectJPARepository.findByName(name.getValue());
        return entityOpt.map(ProjectMapper::toDomain);
    }


    @Override
    public boolean exists(ProjectId id) {
        return projectJPARepository.findById(id.getValue()).isPresent();
    }


    @Override
    public ProjectId save(Project project) {
        ProjectEntity entity = ProjectMapper.toJPA(project);
        projectJPARepository.save(entity);
        return ProjectId.of(entity.getId());
    }


    @CacheEvict(value = {"dashboard_find_by_id", "client_find_by_id", "script_find_by_id"}, allEntries = true)
    @Override
    public void delete(ProjectId id) {
        projectJPARepository.deleteById(id.getValue());
    }


}

package gnoolson.saturday.app.import_export;

import gnoolson.saturday.app.repository.project.ProjectJPARepository;
import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.port.outbound.ProjectChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProjectCheckerImpl implements ProjectChecker {

    private final ProjectJPARepository projectJPARepository;

    /*
     *
     *
     * */
    @Override
    public boolean exists(ProjectId id) {
        Optional<ProjectEntity> projectOpt = projectJPARepository.findById(id.getValue());
        return projectOpt.isPresent();
    }

}

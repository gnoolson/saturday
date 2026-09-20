package gnoolson.saturday.app.project;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.project.model.entity.Project;
import gnoolson.saturday.project.port.outbound.ProjectRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProjectIdCheckerGatewayImpl implements gnoolson.saturday.dashboard.port.outbound.ProjectIdCheckerGateway,
        gnoolson.saturday.schedule.port.outbound.ProjectIdCheckerGateway,
        gnoolson.saturday.script.port.outbound.ProjectIdCheckerGateway,
        gnoolson.saturday.client.port.outbound.ProjectIdCheckerGateway {

    private final ProjectRepositoryGateway projectRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public boolean exists(ProjectId id) {
        Optional<Project> projectOpt = projectRepositoryGateway.find(id);
        return projectOpt.isPresent();
    }

}

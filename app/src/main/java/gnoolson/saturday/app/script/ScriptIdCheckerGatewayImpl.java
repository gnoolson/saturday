package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.schedule.port.outbound.ScriptIdCheckerGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScriptIdCheckerGatewayImpl implements ScriptIdCheckerGateway,
        gnoolson.saturday.dashboard.port.outbound.ScriptIdCheckerGateway,
        gnoolson.saturday.subscription.port.outbound.ScriptIdCheckerGateway,
        gnoolson.saturday.client.port.outbound.ScriptIdCheckerGateway {

    private final ScriptRepositoryGateway scriptRepositoryGateway;

    @Override
    public boolean exists(ProjectId projectId, ScriptId id) {
        return scriptRepositoryGateway.find(projectId, id).isPresent();
    }

}

package gnoolson.saturday.dashboard.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;


public interface ScriptIdCheckerGateway {

    boolean exists(ProjectId projectId, ScriptId id);

}

package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;


public interface ScriptIdCheckerGateway {

    boolean exists(ProjectId projectId, ScriptId scriptId);

}

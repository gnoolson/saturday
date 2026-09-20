package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;


public interface ProjectIdCheckerGateway {

    boolean exists(ProjectId id);

}

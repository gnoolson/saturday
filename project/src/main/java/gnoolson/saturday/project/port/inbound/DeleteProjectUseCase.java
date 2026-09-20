package gnoolson.saturday.project.port.inbound;

import gnoolson.saturday.common.model.vo.ProjectId;


public interface DeleteProjectUseCase {

    boolean execute(ProjectId id);

}

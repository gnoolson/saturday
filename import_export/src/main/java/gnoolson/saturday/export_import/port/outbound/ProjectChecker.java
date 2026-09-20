package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;

public interface ProjectChecker {

    boolean exists(ProjectId id);

}

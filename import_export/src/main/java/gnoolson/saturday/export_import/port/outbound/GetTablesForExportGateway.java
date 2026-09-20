package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.model.vo.Table;

import java.util.List;
import java.util.Set;


public interface GetTablesForExportGateway {

    List<Table> execute(Set<ProjectId> projectIdSet);

}

package gnoolson.saturday.export_import.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.model.vo.Table;

import java.util.function.Predicate;

public interface ImportTablesGateway {

    void execute(Predicate<ProjectId> predicate, Table... tables);

}

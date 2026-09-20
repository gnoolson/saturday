package gnoolson.saturday.export_import.application;

import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.model.vo.Table;
import gnoolson.saturday.export_import.model.vo.TableName;
import gnoolson.saturday.export_import.port.inbound.ImportUseCase;
import gnoolson.saturday.export_import.port.outbound.DeleteTempFolderGateway;
import gnoolson.saturday.export_import.port.outbound.ImportTablesGateway;
import gnoolson.saturday.export_import.port.outbound.ReadImportFileGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImportUseCaseImpl implements ImportUseCase {

    private final ReadImportFileGateway readImportFileGateway;
    private final ImportTablesGateway importTablesGateway;
    private final DeleteTempFolderGateway deleteTempFolderGateway;

    /*
     *
     *
     * */
    @Override
    public void execute(ImportProjectsDto importProjectsDto) {
        Path path = importProjectsDto.getPath();

        try {
            Table projectsTable = readImportFileGateway.execute(TableName.PROJECTS, path);
            Table scriptsTable = readImportFileGateway.execute(TableName.SCRIPTS, path);
            Table clientsTable = readImportFileGateway.execute(TableName.CLIENTS, path);
            Table subscriptionsTable = readImportFileGateway.execute(TableName.SUBSCRIPTIONS, path);
            Table dashboardsTable = readImportFileGateway.execute(TableName.DASHBOARDS, path);
            Table schedulesTable = readImportFileGateway.execute(TableName.SCHEDULES, path);

            importTablesGateway.execute((projectId) -> importProjectsDto.getProjectIdSet().contains(projectId), projectsTable, scriptsTable, clientsTable, subscriptionsTable, dashboardsTable, schedulesTable);
        } finally {
            deleteTempFolderGateway.execute(path);
        }
    }

}

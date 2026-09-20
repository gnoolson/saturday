package gnoolson.saturday.export_import.application;

import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.model.vo.Table;
import gnoolson.saturday.export_import.model.vo.TableName;
import gnoolson.saturday.export_import.port.inbound.ImportDemoProjectsUseCase;
import gnoolson.saturday.export_import.port.outbound.*;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ImportDemoProjectsUseCaseImpl implements ImportDemoProjectsUseCase {

    private final ReadImportFileGateway readImportFileGateway;
    private final UnzipImportFilesGateway unzipImportFilesGateway;
    private final DeleteTempFolderGateway deleteTempFolderGateway;
    private final ImportTablesGateway importTablesGateway;
    private final DemoRepositoryGateway demoRepositoryGateway;
    private final GetDemoImportFileGateway getDemoImportFileGateway;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        boolean installed = demoRepositoryGateway.get();
        if (installed)
            return;

        Optional<ImportFile> importFileOpt = getDemoImportFileGateway.execute();
        if (!importFileOpt.isPresent())
            return;

        Path path = unzipImportFilesGateway.execute(importFileOpt.get());
        try {
            Table projectsTable = readImportFileGateway.execute(TableName.PROJECTS, path);
            Table scriptsTable = readImportFileGateway.execute(TableName.SCRIPTS, path);
            Table clientsTable = readImportFileGateway.execute(TableName.CLIENTS, path);
            Table subscriptionsTable = readImportFileGateway.execute(TableName.SUBSCRIPTIONS, path);
            Table dashboardsTable = readImportFileGateway.execute(TableName.DASHBOARDS, path);
            Table schedulesTable = readImportFileGateway.execute(TableName.SCHEDULES, path);

            importTablesGateway.execute((projectId) -> true, projectsTable, scriptsTable, clientsTable, subscriptionsTable, dashboardsTable, schedulesTable);

            demoRepositoryGateway.save(true);
        } finally {
            deleteTempFolderGateway.execute(path);
        }
    }


}

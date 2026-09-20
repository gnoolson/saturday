package gnoolson.saturday.export_import.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.export_import.model.vo.*;
import gnoolson.saturday.export_import.port.inbound.ExportUseCase;
import gnoolson.saturday.export_import.port.outbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExportUseCaseImpl implements ExportUseCase {

    private final GetTablesForExportGateway getTablesForExportGateway;
    private final WriteTableInCSVFileGateway writeTableInCSVFileGateway;
    private final ZipExportFilesGateway zipExportFilesGateway;
    private final CreateTempDirForExportGateway createTempDirForExportGateway;
    private final DeleteTempFolderGateway deleteTempFolderGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public FileName execute(ExportProjectsDto exportProjectsDto) {
        try (Locker.LockHandle ignore = locker.lockGlobal()) {
            List<Table> tables = getTablesForExportGateway.execute(exportProjectsDto.getProjectIdSet());
            List<File> tempFiles = new ArrayList<>(tables.size());
            Path tempDir = createTempDirForExportGateway.execute();

            try {
                for (Table table : tables) {
                    TableName tableName = table.getName();
                    List<FieldName> fields = table.getFields();
                    List<Row> rows = table.getRows();
                    tempFiles.add(writeTableInCSVFileGateway.execute(tempDir, tableName, fields, rows));
                }

                return zipExportFilesGateway.execute(tempFiles);
            } finally {
                deleteTempFolderGateway.execute(tempDir);
            }
        }
    }

}

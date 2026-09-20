package gnoolson.saturday.export_import.application;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.export_import.model.vo.*;
import gnoolson.saturday.export_import.port.inbound.UploadImportFileUseCase;
import gnoolson.saturday.export_import.port.outbound.ProjectChecker;
import gnoolson.saturday.export_import.port.outbound.ReadImportFileGateway;
import gnoolson.saturday.export_import.port.outbound.UnzipImportFilesGateway;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UploadImportFileUseCaseImpl implements UploadImportFileUseCase {

    private final UnzipImportFilesGateway unzipImportFilesGateway;
    private final ReadImportFileGateway readImportFileGateway;
    private final ProjectChecker projectChecker;

    /*
     *
     *
     * */
    @Override
    public UploadResultDto execute(FileName fileName, ImportFile importFile) {
        Path path = unzipImportFilesGateway.execute(importFile);
        Table projectsTable = readImportFileGateway.execute(TableName.PROJECTS, path);
        List<ProjectDto> projects = readProjects(projectsTable);

        return new UploadResultDto(fileName, projects, path);
    }

    /*
     *
     *
     * */
    private List<ProjectDto> readProjects(Table projectsTable) {
        List<Row> rows = projectsTable.getRows();
        List<FieldName> fields = projectsTable.getFields();

        int projectNameIndex = -1;
        int projectIdIndex = -1;

        for (int i = 0; i < fields.size(); i++) {
            FieldName fieldName = fields.get(i);

            if (fieldName == FieldName.PROJECT_NAME) {
                projectNameIndex = i;
            }

            if (fieldName == FieldName.PROJECT_ID) {
                projectIdIndex = i;
            }
        }

        List<ProjectDto> result = new ArrayList<>(rows.size());

        for (Row row : rows) {
            ProjectName name = ProjectName.of(row.getValues().get(projectNameIndex));
            ProjectId id = ProjectId.of(UUID.fromString(row.getValues().get(projectIdIndex)));

            result.add(new ProjectDto(
                    name,
                    id,
                    projectChecker.exists(id)
            ));
        }

        return result;
    }

}

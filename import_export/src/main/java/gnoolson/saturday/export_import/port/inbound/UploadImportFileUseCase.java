package gnoolson.saturday.export_import.port.inbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.export_import.model.vo.FileName;
import gnoolson.saturday.export_import.model.vo.ImportFile;
import gnoolson.saturday.export_import.model.vo.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface UploadImportFileUseCase {

    UploadResultDto execute(FileName fileName, ImportFile importFile);

    @Getter
    @RequiredArgsConstructor
    class UploadResultDto {
        private final FileName fileName;
        private final List<ProjectDto> projects;
        private final Path path;
    }

    @Getter
    @RequiredArgsConstructor
    class ProjectDto {
        private final ProjectName name;
        private final ProjectId id;
        private final boolean alreadyExists;
    }

}

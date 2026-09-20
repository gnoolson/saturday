package gnoolson.saturday.export_import.port.inbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.model.vo.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;


public interface ImportUseCase {

    void execute(ImportProjectsDto importProjectsDto);

    @Getter
    @RequiredArgsConstructor
    class ImportProjectsDto {
        private final Path path;
        private final Set<ProjectId> projectIdSet;
    }

}

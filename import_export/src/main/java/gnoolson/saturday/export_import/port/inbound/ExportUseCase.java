package gnoolson.saturday.export_import.port.inbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.model.vo.FileName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;


public interface ExportUseCase {

    FileName execute(ExportProjectsDto exportProjectsDto);

    @Getter
    @RequiredArgsConstructor
    class ExportProjectsDto {
        private final Set<ProjectId> projectIdSet;
    }

}

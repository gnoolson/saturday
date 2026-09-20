package gnoolson.saturday.project.port.inbound;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

public interface GetProjectsForExportUseCase {

    List<ProjectDto> execute(Set<ProjectId> projectIdSet);

    @Getter
    @RequiredArgsConstructor
    class ProjectDto {
        private final ProjectId id;
        private final ProjectName name;
        private final Description description;
    }

}

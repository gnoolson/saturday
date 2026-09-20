package gnoolson.saturday.project.port.inbound;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface ImportProjectsUseCase {

    void execute(List<ProjectDto> projects);

    @RequiredArgsConstructor
    @Getter
    class ProjectDto {
        private final ProjectId id;
        private final ProjectName name;
        private final Description description;
    }

}

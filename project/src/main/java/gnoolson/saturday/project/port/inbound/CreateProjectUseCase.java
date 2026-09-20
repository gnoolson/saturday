package gnoolson.saturday.project.port.inbound;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface CreateProjectUseCase {

    ProjectId execute(ProjectDto projectDto);

    /*
     *
     *
     * */
    @Getter
    @RequiredArgsConstructor
    class ProjectDto {
        private final ProjectName name;
        private final Description description;
    }

}

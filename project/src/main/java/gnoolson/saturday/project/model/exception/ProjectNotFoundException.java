package gnoolson.saturday.project.model.exception;

import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectNotFoundException extends RuntimeException {

    private final ProjectId projectId;

    @Override
    public String getMessage() {
        return String.format("Project \"%s\" was not found", projectId.getValue().toString());
    }

}

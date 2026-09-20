package gnoolson.saturday.app.repository.project.entity;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.project.model.entity.Project;

import java.util.ArrayList;
import java.util.UUID;

public class ProjectMapper {

    public static Project toDomain(ProjectEntity entity) {
        return new Project(
                ProjectId.of(entity.getId()),
                ProjectName.of(entity.getName()),
                Description.of(entity.getDescription())
        );
    }

    public static ProjectEntity toJPA(Project project) {
        return new ProjectEntity(
                project.getId().isEmpty() ? UUID.randomUUID() : project.getId().getValue(),
                project.getName().getValue(),
                project.getDescription().getValue(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

}





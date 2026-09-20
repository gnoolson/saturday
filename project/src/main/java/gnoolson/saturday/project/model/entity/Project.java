package gnoolson.saturday.project.model.entity;

import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ProjectName;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.Getter;

@Getter
public class Project {

    private final ProjectId id;
    private ProjectName name;
    private Description description;

    /*
     *
     *
     * */
    public Project(ProjectId id, ProjectName name, Description description) {
        DomainModelValidator.checkNotNull(id, "ProjectId");
        DomainModelValidator.checkNotNull(name, "ProjectName");
        DomainModelValidator.checkNotNull(description, "Description");

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Project(ProjectName name, Description description) {
        DomainModelValidator.checkNotNull(name, "ProjectName");
        DomainModelValidator.checkNotNull(description, "Description");

        this.id = ProjectId.empty();
        this.name = name;
        this.description = description;
    }

    public void update(ProjectName name, Description description) {
        DomainModelValidator.checkNotNull(name, "ProjectName");
        DomainModelValidator.checkNotNull(description, "Description");

        this.name = name;
        this.description = description;
    }

}

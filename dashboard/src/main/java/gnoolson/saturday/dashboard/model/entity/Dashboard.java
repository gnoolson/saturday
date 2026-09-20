package gnoolson.saturday.dashboard.model.entity;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.dashboard.model.vo.Html;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Dashboard {

    private final DashboardId id;
    private final ProjectId projectId;
    private DashboardName name;
    private Description description;
    private Html html;
    private ScriptId scriptId;
    private Access access;

    /*
     *
     *
     * */
    public Dashboard(DashboardId id, ProjectId projectId, DashboardName name, Description description, Html html, ScriptId scriptId, Access access) {
        DomainModelValidator.checkNotNull(id, "DashboardId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(name, "DashboardName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(html, "Html");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(access, "Access");

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.html = html;
        this.scriptId = scriptId;
        this.access = access;
    }

    public void update(DashboardName name, Description description, ScriptId scriptId, Access access) {
        DomainModelValidator.checkNotNull(name, "Name");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(access, "Access");

        this.name = name;
        this.description = description;
        this.scriptId = scriptId;
        this.access = access;
    }

    public void update(Html html) {
        DomainModelValidator.checkNotNull(html, "Html");
        this.html = html;
    }

}

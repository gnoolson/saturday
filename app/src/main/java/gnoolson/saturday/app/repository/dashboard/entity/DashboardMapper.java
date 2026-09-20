package gnoolson.saturday.app.repository.dashboard.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.vo.Html;

import java.util.UUID;

public class DashboardMapper {

    public static Dashboard toDomain(DashboardEntity dashboardEntity) {
        return new Dashboard(
                DashboardId.of(dashboardEntity.getId()),
                ProjectId.of(dashboardEntity.getProject().getId()),
                DashboardName.of(dashboardEntity.getName()),
                Description.of(dashboardEntity.getDescription()),
                Html.of(dashboardEntity.getHtml()),
                ScriptId.of(dashboardEntity.getScript().getId()),
                Access.valueOf(dashboardEntity.getAccess())
        );
    }

    public static DashboardEntity toJPA(Dashboard dashboard) {
        return new DashboardEntity(
                dashboard.getId().isEmpty() ? UUID.randomUUID() : dashboard.getId().getValue(),
                new ProjectEntity(dashboard.getProjectId().getValue()),
                dashboard.getName().getValue(),
                dashboard.getDescription().getValue(),
                dashboard.getHtml().getValue(),
                new ScriptEntity(dashboard.getScriptId().getValue()),
                dashboard.getAccess().name()
        );
    }

}

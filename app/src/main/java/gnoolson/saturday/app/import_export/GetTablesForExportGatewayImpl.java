package gnoolson.saturday.app.import_export;

import gnoolson.saturday.app.import_export.dto.ClientAuthDto;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.client.port.inbound.GetClientsForExportUseCase;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardsForExportUseCase;
import gnoolson.saturday.export_import.model.vo.FieldName;
import gnoolson.saturday.export_import.model.vo.Row;
import gnoolson.saturday.export_import.model.vo.Table;
import gnoolson.saturday.export_import.model.vo.TableName;
import gnoolson.saturday.export_import.port.outbound.GetTablesForExportGateway;
import gnoolson.saturday.project.port.inbound.GetProjectsForExportUseCase;
import gnoolson.saturday.schedule.port.inbound.GetSchedulesForExportUseCase;
import gnoolson.saturday.script.port.inbound.GetScriptsForExportUseCase;
import gnoolson.saturday.subscription.port.inbound.GetSubscriptionsForExportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GetTablesForExportGatewayImpl implements GetTablesForExportGateway {

    private final GetProjectsForExportUseCase getProjectsForExportUseCase;
    private final GetClientsForExportUseCase getClientsForExportUseCase;
    private final GetScriptsForExportUseCase getScriptsForExportUseCase;
    private final GetSubscriptionsForExportUseCase getSubscriptionsForExportUseCase;
    private final GetSchedulesForExportUseCase getSchedulesForExportUseCase;
    private final GetDashboardsForExportUseCase getDashboardsForExportUseCase;

    /*
     *
     *
     * */
    @Override
    public List<Table> execute(Set<ProjectId> projectIdSet) {
        List<GetProjectsForExportUseCase.ProjectDto> projects = getProjectsForExportUseCase.execute(projectIdSet);
        Table projectsTable = projectsTotable(projects);

        List<GetScriptsForExportUseCase.ScriptDto> scripts = getScriptsForExportUseCase.execute(projectIdSet);
        Table scriptsTable = scriptsToTable(scripts);

        List<GetClientsForExportUseCase.ClientDto> clients = getClientsForExportUseCase.execute(projectIdSet);
        Table clientsTable = clientsToTable(clients);

        List<GetSubscriptionsForExportUseCase.SubscriptionDto> subscriptions = getSubscriptionsForExportUseCase.execute(projectIdSet);
        Table subscriptionsTable = subscriptionsToTable(subscriptions);

        List<GetSchedulesForExportUseCase.ScheduleDto> schedules = getSchedulesForExportUseCase.execute(projectIdSet);
        Table schedulesTable = schedulesToTable(schedules);

        List<GetDashboardsForExportUseCase.DashboardDto> dashboards = getDashboardsForExportUseCase.execute(projectIdSet);
        Table dashboardsTable = dashboardsToTable(dashboards);

        return Arrays.asList(projectsTable, scriptsTable, clientsTable, subscriptionsTable, schedulesTable, dashboardsTable);
    }

    /*
     *
     *
     *
     * */
    private Table projectsTotable(List<GetProjectsForExportUseCase.ProjectDto> projects) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.PROJECT_ID,
                FieldName.PROJECT_NAME,
                FieldName.PROJECT_DESCRIPTION
        );

        List<Row> rows = new ArrayList<>(projects.size());
        for (GetProjectsForExportUseCase.ProjectDto project : projects) {
            rows.add(new Row(
                    Arrays.asList(
                            project.getId().getValue().toString(),
                            project.getName().getValue(),
                            project.getDescription().getValue()
                    )
            ));
        }

        return new Table(TableName.PROJECTS, fieldNames, rows);
    }

    private Table dashboardsToTable(List<GetDashboardsForExportUseCase.DashboardDto> dashboards) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.DASHBOARD_ID,
                FieldName.DASHBOARD_NAME,
                FieldName.DASHBOARD_DESCRIPTION,
                FieldName.DASHBOARD_HTML,
                FieldName.DASHBOARD_SCRIPT_ID,
                FieldName.DASHBOARD_ACCESS,
                FieldName.DASHBOARD_PROJECT_ID
        );

        List<Row> rows = new ArrayList<>(dashboards.size());
        for (GetDashboardsForExportUseCase.DashboardDto dashboard : dashboards) {
            rows.add(new Row(
                    Arrays.asList(
                            dashboard.getId().getValue().toString(),
                            dashboard.getName().getValue(),
                            dashboard.getDescription().getValue(),
                            dashboard.getHtml().getValue(),
                            dashboard.getScriptId().getValue().toString(),
                            dashboard.getAccess().toString(),
                            dashboard.getProjectId().getValue().toString()
                    )
            ));
        }

        return new Table(TableName.DASHBOARDS, fieldNames, rows);
    }

    private Table schedulesToTable(List<GetSchedulesForExportUseCase.ScheduleDto> schedules) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.SCHEDULE_ID,
                FieldName.SCHEDULE_NAME,
                FieldName.SCHEDULE_DESCRIPTION,
                FieldName.SCHEDULE_CRON_EXPRESSION,
                FieldName.SCHEDULE_SCRIPT_ID,
                FieldName.SCHEDULE_PROJECT_ID
        );

        List<Row> rows = new ArrayList<>(schedules.size());
        for (GetSchedulesForExportUseCase.ScheduleDto schedule : schedules) {
            rows.add(new Row(
                    Arrays.asList(
                            schedule.getId().getValue().toString(),
                            schedule.getName().getValue(),
                            schedule.getDescription().getValue(),
                            schedule.getCronExpression().getValue(),
                            schedule.getScriptId().getValue().toString(),
                            schedule.getProjectId().getValue().toString()
                    )
            ));
        }

        return new Table(TableName.SCHEDULES, fieldNames, rows);
    }

    private Table subscriptionsToTable(List<GetSubscriptionsForExportUseCase.SubscriptionDto> subscriptions) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.SUBSCRIPTION_ID,
                FieldName.SUBSCRIPTION_CLIENT_ID,
                FieldName.SUBSCRIPTION_SCRIPT_ID,
                FieldName.SUBSCRIPTION_DESCRIPTION,
                FieldName.SUBSCRIPTION_TOPIC_FILTER,
                FieldName.SUBSCRIPTION_PROJECT_ID
        );

        List<Row> rows = new ArrayList<>(subscriptions.size());
        for (GetSubscriptionsForExportUseCase.SubscriptionDto subscription : subscriptions) {
            rows.add(new Row(
                    Arrays.asList(
                            subscription.getId().getValue().toString(),
                            subscription.getClientId().getValue().toString(),
                            subscription.getScriptId().getValue().toString(),
                            subscription.getDescription().getValue(),
                            subscription.getTopicFilter().getValue(),
                            subscription.getProjectId().getValue().toString()
                    )
            ));
        }

        return new Table(TableName.SUBSCRIPTIONS, fieldNames, rows);
    }

    private Table clientsToTable(List<GetClientsForExportUseCase.ClientDto> clients) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.CLIENT_ID,
                FieldName.CLIENT_NAME,
                FieldName.CLIENT_DESCRIPTION,
                FieldName.CLIENT_SERVER_URI,
                FieldName.CLIENT_AUTH,
                FieldName.CLIENT_CONNECTED_EVENT_HANDLER,
                FieldName.CLIENT_DISCONNECTED_EVENT_HANDLER,
                FieldName.CLIENT_PROJECT_ID
        );

        List<Row> rows = new ArrayList<>(clients.size());
        for (GetClientsForExportUseCase.ClientDto client : clients) {
            rows.add(new Row(
                    Arrays.asList(
                            client.getId().getValue().toString(),
                            client.getName().getValue(),
                            client.getDescription().getValue(),
                            client.getServerURI().getValue(),
                            JSON.toJSONString(
                                    new ClientAuthDto(client.getClientAuth().isUse(),
                                            client.getClientAuth().getUsername().getValue(),
                                            client.getClientAuth().getPassword().getValue()
                                    )),
                            client.getDisconnectionEventHandler().getValue().toString(),
                            client.getDisconnectionEventHandler().getValue().toString(),
                            client.getProjectId().getValue().toString()
                    )
            ));
        }

        return new Table(TableName.CLIENTS, fieldNames, rows);
    }

    private Table scriptsToTable(List<GetScriptsForExportUseCase.ScriptDto> scripts) {
        List<FieldName> fieldNames = Arrays.asList(
                FieldName.SCRIPT_ID,
                FieldName.SCRIPT_NAME,
                FieldName.SCRIPT_DESCRIPTION,
                FieldName.SCRIPT_CODE,
                FieldName.SCRIPT_INCLUDED_SCRIPTS,
                FieldName.SCRIPT_CACHING_TIME,
                FieldName.SCRIPT_PROJECT_ID,
                FieldName.SCRIPT_AUTOSTART,
                FieldName.SCRIPT_ERROR_EVENT_HANDLER
        );

        List<Row> rows = new ArrayList<>(scripts.size());
        for (GetScriptsForExportUseCase.ScriptDto script : scripts) {
            rows.add(new Row(
                    Arrays.asList(
                            script.getId().getValue().toString(),
                            script.getName().getValue(),
                            script.getDescription().getValue(),
                            script.getCode().getValue(),
                            JSON.toJSONString(script.getIncludedScripts().stream().map(scriptId -> scriptId.getValue().toString()).toArray(String[]::new)),
                            String.valueOf(script.getCachingTime().getValue()),
                            script.getProjectId().getValue().toString(),
                            String.valueOf(script.isAutostart()),
                            script.getErrorEventHandler().getValue().toString()
                    )
            ));
        }

        return new Table(TableName.SCRIPTS, fieldNames, rows);
    }

}

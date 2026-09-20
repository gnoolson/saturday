package gnoolson.saturday.app.import_export;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.import_export.dto.ClientAuthDto;
import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.client.port.inbound.ImportClientsUseCase;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.ImportDashboardsUseCase;
import gnoolson.saturday.export_import.model.vo.FieldName;
import gnoolson.saturday.export_import.model.vo.Row;
import gnoolson.saturday.export_import.model.vo.Table;
import gnoolson.saturday.export_import.model.vo.TableName;
import gnoolson.saturday.export_import.port.outbound.ImportTablesGateway;
import gnoolson.saturday.project.port.inbound.ImportProjectsUseCase;
import gnoolson.saturday.schedule.model.vo.CronExpression;
import gnoolson.saturday.schedule.port.inbound.ImportSchedulesUseCase;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.ImportScriptsUseCase;
import gnoolson.saturday.subscription.port.inbound.ImportSubscriptionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImportTablesGatewayImpl implements ImportTablesGateway {

    private final ImportProjectsUseCase importProjectsUseCase;
    private final ImportClientsUseCase importClientsUseCase;
    private final ImportScriptsUseCase importScriptsUseCase;
    private final ImportSubscriptionsUseCase importSubscriptionsUseCase;
    private final ImportSchedulesUseCase importSchedulesUseCase;
    private final ImportDashboardsUseCase importDashboardsUseCase;
    private final Locker locker;
    private final TransactionStarter transactionStarter;

    private static void checkField(Table table, int index, FieldName fieldName) {
        FieldName selectedFieldName = table.getFields().get(index);
        if (!selectedFieldName.getName().equals(fieldName.getName()))
            throw new RuntimeException(String.format("Invalid field. Index \"%d\" is not %s", index, fieldName.getName())); // +
    }

    /*
     *
     *
     * */
    @Override
    public void execute(Predicate<ProjectId> predicate, Table... tables) {
        try (Locker.LockHandle ignore = locker.lockIds()) {
            transactionStarter.doIt(() -> {
                setProjects(predicate, getTables(TableName.PROJECTS, tables));
                setScripts(predicate, getTables(TableName.SCRIPTS, tables));
                setClients(predicate, getTables(TableName.CLIENTS, tables));
                setSubscriptions(predicate, getTables(TableName.SUBSCRIPTIONS, tables));
                setDashboards(predicate, getTables(TableName.DASHBOARDS, tables));
                setSchedules(predicate, getTables(TableName.SCHEDULES, tables));
            });
        }
    }

    /*
     *
     *
     * */
    private void setProjects(Predicate<ProjectId> predicate, Table table) {
        List<ImportProjectsUseCase.ProjectDto> projects = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.PROJECT_ID);
            ProjectId id = ProjectId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.PROJECT_NAME);
            ProjectName name = ProjectName.of(row.getValues().get(1));

            checkField(table, 2, FieldName.PROJECT_DESCRIPTION);
            Description description = Description.of(row.getValues().get(2));

            if (!predicate.test(id))
                continue;

            projects.add(new ImportProjectsUseCase.ProjectDto(
                    id,
                    name,
                    description
            ));
        }

        importProjectsUseCase.execute(projects);
    }

    private void setSchedules(Predicate<ProjectId> predicate, Table table) {
        List<ImportSchedulesUseCase.ScheduleDto> schedules = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.SCHEDULE_ID);
            ScheduleId id = ScheduleId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.SCHEDULE_NAME);
            ScheduleName name = ScheduleName.of(row.getValues().get(1));

            checkField(table, 2, FieldName.SCHEDULE_DESCRIPTION);
            Description description = Description.of(row.getValues().get(2));

            checkField(table, 3, FieldName.SCHEDULE_CRON_EXPRESSION);
            CronExpression cronExpression = CronExpression.of(row.getValues().get(3));

            checkField(table, 4, FieldName.SCHEDULE_SCRIPT_ID);
            ScriptId scriptId = ScriptId.of(UUID.fromString(row.getValues().get(4)));

            checkField(table, 5, FieldName.SCHEDULE_PROJECT_ID);
            ProjectId projectId = ProjectId.of(UUID.fromString(row.getValues().get(5)));

            if (!predicate.test(projectId))
                continue;

            schedules.add(new ImportSchedulesUseCase.ScheduleDto(
                    id,
                    projectId,
                    name,
                    description,
                    cronExpression,
                    scriptId
            ));
        }

        importSchedulesUseCase.execute(schedules);
    }

    private void setDashboards(Predicate<ProjectId> predicate, Table table) {
        List<ImportDashboardsUseCase.DashboardDto> dashboards = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.DASHBOARD_ID);
            DashboardId id = DashboardId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.DASHBOARD_NAME);
            DashboardName name = DashboardName.of(row.getValues().get(1));

            checkField(table, 2, FieldName.DASHBOARD_DESCRIPTION);
            Description description = Description.of(row.getValues().get(2));

            checkField(table, 3, FieldName.DASHBOARD_HTML);
            Html html = Html.of(row.getValues().get(3));

            checkField(table, 4, FieldName.DASHBOARD_SCRIPT_ID);
            ScriptId scriptId = ScriptId.of(UUID.fromString(row.getValues().get(4)));

            checkField(table, 5, FieldName.DASHBOARD_ACCESS);
            Access access = Access.valueOf(row.getValues().get(5));

            checkField(table, 6, FieldName.DASHBOARD_PROJECT_ID);
            ProjectId projectId = ProjectId.of(UUID.fromString(row.getValues().get(6)));

            if (!predicate.test(projectId))
                continue;

            dashboards.add(new ImportDashboardsUseCase.DashboardDto(
                    id,
                    projectId,
                    name,
                    description,
                    html,
                    scriptId,
                    access
            ));
        }

        importDashboardsUseCase.execute(dashboards);
    }

    private void setSubscriptions(Predicate<ProjectId> predicate, Table table) {
        List<ImportSubscriptionsUseCase.SubscriptionDto> subscriptions = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.SUBSCRIPTION_ID);
            SubscriptionId id = SubscriptionId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.SUBSCRIPTION_CLIENT_ID);
            ClientId clientId = ClientId.of(UUID.fromString(row.getValues().get(1)));

            checkField(table, 2, FieldName.SUBSCRIPTION_SCRIPT_ID);
            ScriptId scriptId = ScriptId.of(UUID.fromString(row.getValues().get(2)));

            checkField(table, 3, FieldName.SUBSCRIPTION_DESCRIPTION);
            Description description = Description.of(row.getValues().get(3));

            checkField(table, 4, FieldName.SUBSCRIPTION_TOPIC_FILTER);
            TopicFilter topicFilter = TopicFilter.of(row.getValues().get(4));

            checkField(table, 5, FieldName.SUBSCRIPTION_PROJECT_ID);
            ProjectId projectId = ProjectId.of(UUID.fromString(row.getValues().get(5)));

            if (!predicate.test(projectId))
                continue;

            subscriptions.add(new ImportSubscriptionsUseCase.SubscriptionDto(
                    id,
                    clientId,
                    scriptId,
                    description,
                    topicFilter
            ));
        }

        importSubscriptionsUseCase.execute(subscriptions);
    }

    private void setScripts(Predicate<ProjectId> predicate, Table table) {
        List<ImportScriptsUseCase.ScriptDto> scripts = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.SCRIPT_ID);
            ScriptId id = ScriptId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.SCRIPT_NAME);
            ScriptName name = ScriptName.of(row.getValues().get(1));

            checkField(table, 2, FieldName.SCRIPT_DESCRIPTION);
            Description description = Description.of(row.getValues().get(2));

            checkField(table, 3, FieldName.SCRIPT_CODE);
            Code code = Code.of(row.getValues().get(3));

            checkField(table, 4, FieldName.SCRIPT_INCLUDED_SCRIPTS);
            List<ScriptId> includedScripts = JSON.parseArray(row.getValues().get(4), String.class).stream().map(value -> {
                return ScriptId.of(UUID.fromString((String) value));
            }).collect(Collectors.toList());

            checkField(table, 5, FieldName.SCRIPT_CACHING_TIME);
            CachingTime cachingTime = CachingTime.of(Integer.parseInt(row.getValues().get(5)));

            checkField(table, 6, FieldName.SCRIPT_PROJECT_ID);
            ProjectId projectId = ProjectId.of(UUID.fromString(row.getValues().get(6)));

            checkField(table, 7, FieldName.SCRIPT_AUTOSTART);
            boolean autostart = Boolean.parseBoolean(row.getValues().get(7));

            checkField(table, 8, FieldName.SCRIPT_ERROR_EVENT_HANDLER);
            ScriptId errorHandler = ScriptId.of(UUID.fromString(row.getValues().get(8)));

            if (!predicate.test(projectId))
                continue;

            scripts.add(new ImportScriptsUseCase.ScriptDto(
                    id,
                    projectId,
                    name,
                    description,
                    code,
                    includedScripts,
                    cachingTime,
                    autostart,
                    errorHandler
            ));
        }

        importScriptsUseCase.execute(scripts);
    }

    private void setClients(Predicate<ProjectId> predicate, Table table) {
        List<ImportClientsUseCase.ClientDto> clients = new ArrayList<>();

        for (Row row : table.getRows()) {
            checkField(table, 0, FieldName.CLIENT_ID);
            ClientId id = ClientId.of(UUID.fromString(row.getValues().get(0)));

            checkField(table, 1, FieldName.CLIENT_NAME);
            ClientName name = ClientName.of(row.getValues().get(1));

            checkField(table, 2, FieldName.CLIENT_DESCRIPTION);
            Description description = Description.of(row.getValues().get(2));

            checkField(table, 3, FieldName.CLIENT_SERVER_URI);
            ServerURI serverURI = ServerURI.of(row.getValues().get(3));

            checkField(table, 4, FieldName.CLIENT_AUTH);
            ClientAuthDto clientAuthDto = JSON.parseObject(row.getValues().get(4), ClientAuthDto.class);
            ClientAuth clientAuth = ClientAuth.of(clientAuthDto.isUse(),
                    AuthUsername.of(clientAuthDto.getUsername()),
                    AuthPassword.of(clientAuthDto.getPassword())
            );

            checkField(table, 5, FieldName.CLIENT_CONNECTED_EVENT_HANDLER);
            ScriptId connectionEventHandlerScriptId = ScriptId.of(UUID.fromString(row.getValues().get(5)));

            checkField(table, 6, FieldName.CLIENT_DISCONNECTED_EVENT_HANDLER);
            ScriptId disconnectionEventHandlerScriptId = ScriptId.of(UUID.fromString(row.getValues().get(6)));

            checkField(table, 7, FieldName.CLIENT_PROJECT_ID);
            ProjectId projectId = ProjectId.of(UUID.fromString(row.getValues().get(7)));

            if (!predicate.test(projectId))
                continue;

            clients.add(new ImportClientsUseCase.ClientDto(
                    id,
                    projectId,
                    name,
                    description,
                    serverURI,
                    clientAuth,
                    connectionEventHandlerScriptId,
                    disconnectionEventHandlerScriptId
            ));
        }

        importClientsUseCase.execute(clients);
    }

    private Table getTables(TableName tableName, Table... tables) {
        for (Table table : tables) {
            if (table.getName().equals(tableName))
                return table;
        }
        throw new RuntimeException(String.format("Table \"%s\" was not found", tableName.name())); // +
    }

}

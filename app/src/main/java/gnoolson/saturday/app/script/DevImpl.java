package gnoolson.saturday.app.script;

import gnoolson.saturday.app.script.launch.ScriptLauncherForTest;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProvider;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.internal_lua_libs.args.Args;
import gnoolson.saturday.internal_lua_libs.client.Client;
import gnoolson.saturday.internal_lua_libs.client.PublishMessageGateway;
import gnoolson.saturday.internal_lua_libs.client_info.ClientInfo;
import gnoolson.saturday.internal_lua_libs.client_info.ClientsInProjectProviderGateway;
import gnoolson.saturday.internal_lua_libs.dashboard.Dashboard;
import gnoolson.saturday.internal_lua_libs.dashboard.IncomingMessage;
import gnoolson.saturday.internal_lua_libs.dashboard.SendDashboardDataGateway;
import gnoolson.saturday.internal_lua_libs.log.Id;
import gnoolson.saturday.internal_lua_libs.log.Log;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardFunctionality;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardsProviderGateway;
import gnoolson.saturday.lua_script_executor.Output;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.model.vo.DefaultField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DevImpl implements Dev {

    private final ScriptLauncherForTest scriptLauncher;
    private final LuaLibFunctionalityProvider functionalityProvider;
    private final PublishMessageGateway publishMessageGateway;
    private final ClientsInProjectProviderGateway clientsInProjectProviderGateway;
    private final gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache;
    private final SendDashboardDataGateway sendDashboardDataGateway;
    private final OpenDashboardsProviderGateway openDashboardsProviderGateway;

    /*
     *
     *
     * */
    @Override
    public ResponseDto execute(RequestDto requestDto) {
        ScriptId scriptId = requestDto.getId();

        Output output = new Output();
        TimeInMs executionTime;

        Collection<Functionality> functionalities = functionalityProvider.getFunctionalities();

        setOutput(functionalities, output);
        setupDashboard(functionalities, requestDto.getProjectId(), requestDto.getDashboard());
        setupClient(functionalities, requestDto.getClientDto(), requestDto.isWriteOutgoingMessageToOutput());
        setupClientsInfo(functionalities);
        setupLog(functionalities, scriptId, requestDto.isWriteLogToOutput(), requestDto.isLogDebugEnabled());
        setupArgs(requestDto.getProjectId(), functionalities, scriptId, requestDto.getArgs());
        setupOpenDashboard(functionalities);

        Code code = requestDto.getCode();
        Collection<ScriptId> includedScripts = requestDto.getIncludedScripts();

        scriptId = ScriptId.random(); // Need to change real id to random id
        long execute = scriptLauncher.execute(functionalities, requestDto.getProjectId(), scriptId, code, includedScripts);
        executionTime = TimeInMs.of(execute);

        return new ResponseDto(output, executionTime);
    }


    /*
     *
     *
     * */
    private void setupOpenDashboard(Collection<Functionality> functionalities) {
        OpenDashboardFunctionality openDashboardFunctionality = (OpenDashboardFunctionality) find(functionalities, gnoolson.saturday.internal_lua_libs.open_dashboard.Id.VALUE);
        openDashboardFunctionality.setup(openDashboardsProviderGateway);
    }

    private void setupLog(Collection<Functionality> functionalities, ScriptId scriptId, boolean toOutput, boolean debug) {
        if (scriptId.isEmpty())
            throw new IllegalArgumentException("ScriptId cannot be empty"); // +

        Log log = (Log) find(functionalities, Id.VALUE);

        LogGateway logGateway = logCache.getAndProlongOrCreate(scriptId);

        if (!toOutput)
            logGateway.setDebugEnabled(debug);

        log.useOutput(toOutput);
        log.setup(logGateway, debug);
    }

    private void setOutput(Collection<Functionality> functionalities, Output output) {
        for (Functionality functionality : functionalities) {
            functionality.writeResultToOutput(output);
        }
    }

    private void setupDashboard(Collection<Functionality> functionalities, ProjectId projectId, DashboardMessageDto dashboardMessageDto) {
        Dashboard dashboard = (Dashboard) find(functionalities, gnoolson.saturday.internal_lua_libs.dashboard.Id.VALUE);
        if (dashboardMessageDto.isEmpty()) {
            dashboard.setup(sendDashboardDataGateway);
        } else {
            dashboard.setup(sendDashboardDataGateway, OpenDashboardId.of(dashboardMessageDto.getOpenDashboardId(),
                    DashboardId.of(dashboardMessageDto.getDashboardId())), new IncomingMessage(dashboardMessageDto.getData()));
        }
    }

    private void setupClient(Collection<Functionality> functionalities, ClientDto clientDto, boolean toOutput) {
        Client client = (Client) find(functionalities, gnoolson.saturday.internal_lua_libs.client.Id.VALUE);
        client.useOutput(toOutput);

        if (clientDto.isEmpty()) {
            client.setup(publishMessageGateway);
        } else {
            gnoolson.saturday.internal_lua_libs.client.IncomingMessage incomingMessage = new gnoolson.saturday.internal_lua_libs.client.IncomingMessage(
                    clientDto.getTopic(),
                    clientDto.getTopicFilter(),
                    clientDto.getQos(),
                    clientDto.getPayload()
            );

            client.setup(clientDto.getClientId(), publishMessageGateway, incomingMessage);
        }
    }

    private void setupClientsInfo(Collection<Functionality> functionalities) {
        ClientInfo clientInfo = (ClientInfo) find(functionalities, gnoolson.saturday.internal_lua_libs.client_info.Id.VALUE);
        clientInfo.setup(clientsInProjectProviderGateway);
    }

    private void setupArgs(ProjectId projectId, Collection<Functionality> functionalities, ScriptId scriptId, ArgsDto argsDto) {
        Map<String, Object> data = argsDto.getData();
        data.put(DefaultField.SCRIPT_ID, scriptId.getValue().toString());
        data.put(DefaultField.PROJECT_ID, projectId.getValue().toString());

        if (!data.containsKey(DefaultField.SOURCE))
            data.put(DefaultField.SOURCE, "DEV");

        Args args = (Args) find(functionalities, gnoolson.saturday.internal_lua_libs.args.Id.VALUE);
        args.setup(argsDto.getData());
    }

    private Functionality find(Collection<Functionality> functionalities, String id) {
        for (Functionality functionality : functionalities) {
            if (functionality.getLuaLibId().equals(id))
                return functionality;
        }

        throw new RuntimeException("Functionality was not found"); // +
    }

}


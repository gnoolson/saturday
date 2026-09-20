package gnoolson.saturday.app.script.launch;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.script.ScriptData;
import gnoolson.saturday.app.script.ScriptDataProvider;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProvider;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.client.Client;
import gnoolson.saturday.internal_lua_libs.client.PublishMessageGateway;
import gnoolson.saturday.internal_lua_libs.client_info.ClientInfo;
import gnoolson.saturday.internal_lua_libs.client_info.ClientsInProjectProviderGateway;
import gnoolson.saturday.internal_lua_libs.dashboard.Dashboard;
import gnoolson.saturday.internal_lua_libs.dashboard.SendDashboardDataGateway;
import gnoolson.saturday.internal_lua_libs.log.Log;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardFunctionality;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardsProviderGateway;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.script.application.ScriptErrorHandler;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class CommonScriptLauncher {

    protected final ScriptErrorHandler scriptErrorHandler;
    protected final ScriptExecutor scriptExecutor;
    protected final ScriptRepositoryGateway scriptRepositoryGateway;
    protected final Locker locker;
    protected final LuaLibFunctionalityProvider luaLibFunctionalityProvider;
    protected final PublishMessageGateway publishMessageGateway;
    protected final ClientsInProjectProviderGateway clientsInProjectProviderGateway;
    protected final Cache<ScriptId, LogGateway> logCache;
    protected final ScriptDataProvider scriptDataProvider = new ScriptDataProviderImpl();
    protected final SendDashboardDataGateway sendDashboardDataGateway;
    protected final OpenDashboardsProviderGateway openDashboardsProviderGateway;

    /*
     *
     *
     * */
    protected void setupOpenDashboard(Collection<Functionality> functionalities) {
        OpenDashboardFunctionality openDashboardFunctionality = (OpenDashboardFunctionality) find(functionalities, gnoolson.saturday.internal_lua_libs.open_dashboard.Id.VALUE);
        openDashboardFunctionality.setup(openDashboardsProviderGateway);
    }

    protected boolean isScriptEnabled(Script script) {
        return script.isEnabled();
    }

    protected void setupClient(Collection<Functionality> functionalities) {
        Client client = (Client) find(functionalities, gnoolson.saturday.internal_lua_libs.client.Id.VALUE);
        client.setup(publishMessageGateway);
    }

    protected void setupClientsInfo(Collection<Functionality> functionalities) {
        ClientInfo clientInfo = (ClientInfo) find(functionalities, gnoolson.saturday.internal_lua_libs.client_info.Id.VALUE);
        clientInfo.setup(clientsInProjectProviderGateway);
    }

    protected void setupLog(ScriptId scriptId, Collection<Functionality> functionalities) {
        LogGateway logGateway = logCache.getAndProlongOrCreate(scriptId);

        Log log = (Log) find(functionalities, gnoolson.saturday.internal_lua_libs.log.Id.VALUE);
        log.setup(logGateway);
    }

    protected Functionality find(Collection<Functionality> functionalities, String id) {
        for (Functionality functionality : functionalities) {
            if (functionality.getLuaLibId().equals(id))
                return functionality;
        }

        throw new RuntimeException("Functionality was not found"); // +
    }

    protected void setupDashboard(Collection<Functionality> functionalities) {
        Dashboard dashboard = (Dashboard) find(functionalities, gnoolson.saturday.internal_lua_libs.dashboard.Id.VALUE);
        dashboard.setup(sendDashboardDataGateway);
    }

    /*
     *
     *
     *
     * */
    private class ScriptDataProviderImpl implements ScriptDataProvider {
        @Override
        public ScriptData execute(ScriptId scriptId) {
            Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));

            List<ScriptData.Script> includedScripts = new ArrayList<>(script.getIncludedScripts().size());
            for (ScriptId includedScriptId : script.getIncludedScripts()) {
                Optional<Script> scriptOpt = scriptRepositoryGateway.find(script.getProjectId(), includedScriptId);
                if (!scriptOpt.isPresent())
                    continue;

                Script includedScript = scriptOpt.get();
                if (!includedScript.isEnabled())
                    continue;

                includedScripts.add(
                        new ScriptData.Script(includedScript.getName(), includedScript.getCode())
                );
            }

            return new ScriptData(
                    new ScriptData.Script(script.getName(), script.getCode()),
                    includedScripts,
                    script.getCachingTime()
            );
        }
    }

}

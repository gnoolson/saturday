package gnoolson.saturday.app.script.launch;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProvider;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.internal_lua_libs.args.Args;
import gnoolson.saturday.internal_lua_libs.client.PublishMessageGateway;
import gnoolson.saturday.internal_lua_libs.client_info.ClientsInProjectProviderGateway;
import gnoolson.saturday.internal_lua_libs.dashboard.SendDashboardDataGateway;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardsProviderGateway;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.script.application.ScriptErrorHandler;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.model.vo.DefaultField;
import gnoolson.saturday.script.port.outbound.AutostartScriptLauncherGateway;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class AutostartScriptLauncherGatewayImpl extends CommonScriptLauncher implements AutostartScriptLauncherGateway {

    private final ErrorScriptLauncher errorScriptLauncher;

    /*
     *
     *
     * */
    public AutostartScriptLauncherGatewayImpl(ScriptErrorHandler scriptErrorHandler,
                                              ScriptExecutor scriptExecutor,
                                              ScriptRepositoryGateway scriptRepositoryGateway,
                                              Locker locker,
                                              LuaLibFunctionalityProvider luaLibFunctionalityProvider,
                                              PublishMessageGateway publishMessageGateway,
                                              ClientsInProjectProviderGateway clientsInProjectProviderGateway,
                                              @Qualifier("LogCache")
                                              Cache<ScriptId, LogGateway> logCache,
                                              ErrorScriptLauncher errorScriptLauncher,
                                              SendDashboardDataGateway sendDashboardDataGateway,
                                              OpenDashboardsProviderGateway openDashboardsProviderGateway) {

        super(scriptErrorHandler, scriptExecutor, scriptRepositoryGateway, locker, luaLibFunctionalityProvider,
                publishMessageGateway, clientsInProjectProviderGateway, logCache, sendDashboardDataGateway, openDashboardsProviderGateway);

        this.errorScriptLauncher = errorScriptLauncher;
    }

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId scriptId) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(scriptId))) {
            Script script = scriptRepositoryGateway.find(scriptId).orElseThrow(() -> new ScriptNotFoundException(scriptId));

            if (!isScriptEnabled(script)) {
                if (log.isDebugEnabled())
                    log.debug("Script \"{}\" is disabled", scriptId.getValue());
                return;
            }

            Collection<Functionality> functionalities = luaLibFunctionalityProvider.getFunctionalities();
            setupClient(functionalities);
            setupClientsInfo(functionalities);
            setupLog(scriptId, functionalities);
            setupDashboard(functionalities);
            setupOpenDashboard(functionalities);

            Args args = (Args) find(functionalities, gnoolson.saturday.internal_lua_libs.args.Id.VALUE);
            Map<String, Object> argsData = new HashMap<>();
            argsData.put(DefaultField.SOURCE, "AUTOSTART");
            argsData.put(DefaultField.SCRIPT_ID, scriptId.getValue().toString());
            argsData.put(DefaultField.PROJECT_ID, script.getProjectId().getValue().toString());
            args.setup(argsData);

            try {
                scriptExecutor.execute(functionalities, scriptId, scriptDataProvider);
            } catch (Exception ex) {
                if (log.isDebugEnabled())
                    log.debug("Exception", ex);

                scriptErrorHandler.execute(scriptId, ex, errorScriptLauncher);
            }
        }
    }

}

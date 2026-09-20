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
import gnoolson.saturday.lua_script_executor.ScriptLauncherGateway;
import gnoolson.saturday.lua_script_executor.lib.Functionality;
import gnoolson.saturday.script.application.ScriptErrorHandler;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.model.exception.ScriptNotFoundException;
import gnoolson.saturday.script.model.vo.DefaultField;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Component
public class ScriptLauncherGatewayImpl extends CommonScriptLauncher implements ScriptLauncherGateway {

    private final ErrorScriptLauncher errorScriptLauncher;

    /*
     *
     *
     * */
    public ScriptLauncherGatewayImpl(ScriptErrorHandler scriptErrorHandler,
                                     ScriptExecutor scriptExecutor,
                                     ScriptRepositoryGateway scriptRepositoryGateway,
                                     Locker locker,
                                     LuaLibFunctionalityProvider luaLibFunctionalityProvider,
                                     PublishMessageGateway publishMessageGateway,
                                     ClientsInProjectProviderGateway clientsInProjectProviderGateway,
                                     @Qualifier("LogCache")
                                     Cache<ScriptId, LogGateway> logCache,
                                     SendDashboardDataGateway sendDashboardDataGateway,
                                     OpenDashboardsProviderGateway openDashboardsProviderGateway,
                                     ErrorScriptLauncher errorScriptLauncher) {

        super(scriptErrorHandler, scriptExecutor, scriptRepositoryGateway, locker, luaLibFunctionalityProvider,
                publishMessageGateway, clientsInProjectProviderGateway, logCache, sendDashboardDataGateway, openDashboardsProviderGateway);

        this.errorScriptLauncher = errorScriptLauncher;
    }

    /*
     *
     *
     * */
    @Override
    public void execute(String scriptIdStr, String luaLibId, Map<String, Object> argsFromPlugin) {
        ScriptId scriptId = ScriptId.of(UUID.fromString(scriptIdStr));

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

            Args argsFunctionality = (Args) find(functionalities, gnoolson.saturday.internal_lua_libs.args.Id.VALUE);

            Map<String, Object> argsData = new HashMap<>();
            argsData.put(DefaultField.SOURCE, "LUA_LIB");
            argsData.put("luaLibId", luaLibId);
            argsData.put(DefaultField.SCRIPT_ID, scriptId.getValue().toString());
            argsData.put(DefaultField.PROJECT_ID, script.getProjectId().getValue().toString());
            argsData.putAll(argsFromPlugin);

            argsFunctionality.setup(argsData);

            scriptExecutor.execute(functionalities, scriptId, scriptDataProvider);
        } catch (Exception ex) {
            if (log.isDebugEnabled())
                log.debug("Exception", ex);

            scriptErrorHandler.execute(scriptId, ex, errorScriptLauncher);
        }
    }

}

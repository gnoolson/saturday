package gnoolson.saturday.app.config;

import gnoolson.locker.Locker;
import gnoolson.saturday.app.log.LogGatewayLog4jImpl;
import gnoolson.saturday.app.script.*;
import gnoolson.saturday.app.script.launch.ScriptExecutor;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProvider;
import gnoolson.saturday.app.script.lib.LuaLibFunctionalityProviderImpl;
import gnoolson.saturday.app.script.lib.LuaLibProvider;
import gnoolson.saturday.app.script.lib.LuaLibProviderImpl;
import gnoolson.saturday.client.application.MQTTClientManager;
import gnoolson.saturday.client.port.outbound.ClientRepositoryGateway;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.cache.Entity;
import gnoolson.saturday.common.cache.EntityProvider;
import gnoolson.saturday.common.eventbus.EventBus;
import gnoolson.saturday.common.eventbus.local.LocalEventBus;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInSec;
import gnoolson.saturday.common.transaction.TransactionStarter;
import gnoolson.saturday.internal_lua_libs.args.ArgsFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.args.ArgsLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.byte_array.PayloadConverterLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.client.ClientFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.client.ClientLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.client_info.ClientInfoFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.client_info.ClientInfoLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.client_info.ClientProviderGateway;
import gnoolson.saturday.internal_lua_libs.client_info.ClientsInProjectProviderGateway;
import gnoolson.saturday.internal_lua_libs.dashboard.DashboardFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.dashboard.DashboardLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.log.LogFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.log.LogGateway;
import gnoolson.saturday.internal_lua_libs.log.LogLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboardLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.output.OutputFunctionalityFactory;
import gnoolson.saturday.internal_lua_libs.output.OutputLuaLibFactory;
import gnoolson.saturday.internal_lua_libs.std.StdLuaLibFactory;
import gnoolson.saturday.lua_script_executor.LuaScriptExecutor;
import gnoolson.saturday.lua_script_executor.LuaScriptExecutorLuajImpl;
import gnoolson.saturday.lua_script_executor.Sandbox;
import gnoolson.saturday.script.application.*;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.*;
import gnoolson.saturday.script.port.outbound.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Configuration
public class ScriptBeanConfig {

    @Bean
    public IsScriptEnabledUseCase scriptBean1(ScriptRepositoryGateway scriptRepositoryGateway) {
        return new IsScriptEnabledUseCaseImpl(scriptRepositoryGateway);
    }

    @Bean
    public GetScriptErrorsCounterUseCase scriptBean2(ScriptErrorRepositoryGateway scriptErrorRepositoryGateway) {
        return new GetScriptErrorsCounterUseCaseImpl(scriptErrorRepositoryGateway);
    }

    @Bean
    public ScriptUpdatedEventListener scriptBean3(@Qualifier("SandboxCache") Cache<ScriptId, Sandbox> sandboxcache,
                                                  EventBus eventBus,
                                                  ScriptRepositoryGateway scriptRepositoryGateway,
                                                  Locker locker) {

        return new ScriptUpdatedEventListener(eventBus, sandboxcache, scriptRepositoryGateway, locker);
    }

    @Bean
    public AutostartScriptsUseCase scriptBean4(ScriptRepositoryGateway scriptRepositoryGateway,
                                               AutostartScriptLauncherGateway autostartScriptLauncherGateway) {

        return new AutostartScriptsUseCaseImpl(scriptRepositoryGateway, autostartScriptLauncherGateway);
    }

    @Bean
    public ClientsInProjectProviderGateway scriptBean5(MQTTClientManager mqttClientManager,
                                                       ClientRepositoryGateway clientRepositoryGateway) {

        return new ClientsProviderGatewayImpl(mqttClientManager, clientRepositoryGateway);
    }

    @Bean
    public LuaLibProvider scriptBean6() {
        LuaLibProvider luaLibProvider = new LuaLibProviderImpl();
        luaLibProvider.addFactory(new DashboardLuaLibFactory());
        luaLibProvider.addFactory(new ClientInfoLuaLibFactory());
        luaLibProvider.addFactory(new LogLuaLibFactory());
        luaLibProvider.addFactory(new ArgsLuaLibFactory());
        luaLibProvider.addFactory(new OpenDashboardLuaLibFactory());
        luaLibProvider.addFactory(new PayloadConverterLuaLibFactory());
        luaLibProvider.addFactory(new ClientLuaLibFactory());
        luaLibProvider.addFactory(new StdLuaLibFactory());
        luaLibProvider.addFactory(new OutputLuaLibFactory());


        return luaLibProvider;
    }

    @Bean
    public LuaLibFunctionalityProvider scriptBean7() {
        LuaLibFunctionalityProvider luaLibFunctionalityProvider = new LuaLibFunctionalityProviderImpl();
        luaLibFunctionalityProvider.addFactory(new DashboardFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new ClientInfoFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new LogFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new ArgsFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new OpenDashboardFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new ClientFunctionalityFactory());
        luaLibFunctionalityProvider.addFactory(new OutputFunctionalityFactory());

        return luaLibFunctionalityProvider;
    }

    @Bean
    public LuaScriptExecutor scriptBean8(@Value("${gnoolson.saturday.script.lua.max_execution_time}") long maxExecutionTime) {
        return new LuaScriptExecutorLuajImpl(maxExecutionTime);
    }

    @Bean("SandboxCache")
    public gnoolson.saturday.common.cache.Cache<ScriptId, Sandbox> scriptBean9(@Value("${gnoolson.saturday.script.sandbox_storage.clean_interval}") int cleanInterval,
                                                                               TimeProvider timeProvider,
                                                                               Locker locker) {

        return new gnoolson.saturday.common.cache.Cache<>(TimeInSec.of(cleanInterval), timeProvider, locker);
    }

    @Bean
    public ScriptExecutor scriptBean10(@Value("${gnoolson.saturday.script.lua.lib}") String luaLibPath,
                                       @Value("${gnoolson.saturday.script.lua.full_access}") boolean luajFullAccess,
                                       LuaScriptExecutor luaScriptExecutor,
                                       @Qualifier("SandboxCache")
                                       gnoolson.saturday.common.cache.Cache<ScriptId, Sandbox> cache,
                                       LuaLibProvider luaLibProvider) throws FileNotFoundException {

        File file = ResourceUtils.getFile(luaLibPath);
        return new ScriptExecutor(file, luajFullAccess, luaScriptExecutor, cache, luaLibProvider);
    }


    @Bean
    public UpdateCodeUseCase scriptBean11(TransactionStarter transactionStarter,
                                          Locker locker,
                                          ScriptRepositoryGateway scriptRepositoryGateway,
                                          EventBus eventBus) {

        return new UpdateCodeUseCaseImpl(transactionStarter, locker, scriptRepositoryGateway, eventBus);
    }

    @Bean
    public BeautifyCodeUseCase scriptBean12(BeautifyCodeGateway beautifyCodeGateway) {
        return new BeautifyCodeUseCaseImpl(beautifyCodeGateway);
    }

    @Bean
    public ImportScriptsUseCase scriptBean28(ScriptRepositoryGateway scriptRepositoryGateway,
                                             TransactionStarter transactionStarter,
                                             EventBus eventBus) {

        return new ImportScriptsUseCaseImpl(scriptRepositoryGateway, transactionStarter, eventBus);
    }

    @Bean
    public GetScriptsForExportUseCase scriptBean13(ScriptRepositoryGateway scriptRepositoryGateway) {
        return new GetScriptsForExportUseCaseImpl(scriptRepositoryGateway);
    }

    @Bean
    public GetDefaultScriptCodeUseCase scriptBean14(@Value("${gnoolson.saturday.script.template}") String path) throws IOException {
        File file = ResourceUtils.getFile(path);
        String code = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        return new GetDefaultScriptCodeUseCaseImpl(Code.of(code));
    }

    @Bean
    public ClearLogFileGateway scriptBean15(@Qualifier("LogCache") gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache, Locker locker) {
        return new ClearLogFileGatewayImpl(logCache, locker);
    }

    @Bean
    public ClearLogFileUseCase scriptBean16(ClearLogFileGateway clearLogFileGateway) {
        return new ClearLogUseCaseImpl(clearLogFileGateway);
    }

    @Bean
    public GetLogTextUseCase scriptBean29(@Value("${gnoolson.saturday.script.log_storage.path}") String folderPath,
                                          ScriptLogReaderGateway scriptLogReaderGateway) throws FileNotFoundException {

        File folder = ResourceUtils.getFile(folderPath);
        return new GetLogTextUseCaseImpl(folder, scriptLogReaderGateway);
    }

    @Bean
    public ScriptLogReaderGateway scriptBean17() {
        return new ScriptLogReaderGatewayImpl();
    }

    @Bean("LogCache")
    public gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> scriptBean18(@Value("${gnoolson.saturday.script.log.cache.storage_time}") int storageTime,
                                                                                   @Value("${gnoolson.saturday.script.log.cache.clean_interval}") int cleanInterval,
                                                                                   @Value("${gnoolson.saturday.script.log_storage.path}") String folderPath,
                                                                                   TimeProvider timeProvider, Locker locker) throws FileNotFoundException {

        File folder = ResourceUtils.getFile(folderPath);

        return new gnoolson.saturday.common.cache.Cache<>(TimeInSec.of(cleanInterval), timeProvider, new EntityProvider<ScriptId, LogGateway>() {
            @Override
            public Entity<LogGateway> create(ScriptId scriptId) {
                LogGatewayLog4jImpl logGatewayLog4j = new LogGatewayLog4jImpl(scriptId, folder);

                return new Entity<LogGateway>() {
                    @Override
                    public CachingTime getCachingTime() {
                        return CachingTime.of(storageTime);
                    }

                    @Override
                    public LogGateway getValue() {
                        return logGatewayLog4j;
                    }

                    @Override
                    public void destroy() {
                        logGatewayLog4j.close();
                    }
                };
            }
        }, locker);
    }

    @Bean
    public ScriptControlUseCase scriptBean19(ScriptRepositoryGateway scriptRepositoryGateway,
                                             TransactionStarter transactionStarter,
                                             Locker locker,
                                             EventBus eventBus) {

        return new ScriptControlUseCaseImpl(scriptRepositoryGateway, transactionStarter, locker, eventBus);
    }

    @Bean
    public DeleteScriptUseCase scriptBean20(ScriptRepositoryGateway scriptRepositoryGateway,
                                            TransactionStarter transactionStarter,
                                            LocalEventBus localEventBus,
                                            Locker locker) {

        return new DeleteScriptUseCaseImpl(scriptRepositoryGateway, transactionStarter, localEventBus, locker);
    }

    @Bean
    public DeleteErrorsAndUnblockUseCase scriptBean21(ScriptRepositoryGateway scriptRepositoryGateway,
                                                      ScriptErrorRepositoryGateway scriptErrorRepositoryGateway,
                                                      TransactionStarter transactionStarter) {

        return new DeleteErrorsAndUnblockUseCaseImpl(scriptErrorRepositoryGateway, scriptRepositoryGateway, transactionStarter);
    }


    @Bean
    public ScriptErrorHandler scriptBean22(TimeProvider timeProvider, TransactionStarter transactionStarter,
                                           ScriptErrorRepositoryGateway scriptErrorRepositoryGateway,
                                           @Value("${gnoolson.saturday.script.max_errors}") int maxErrors,
                                           ScriptRepositoryGateway scriptRepositoryGateway, EventBus eventBus
    ) {
        return new ScriptErrorHandlerImpl(
                timeProvider,
                transactionStarter,
                scriptErrorRepositoryGateway,
                PositiveNumber.of(maxErrors),
                scriptRepositoryGateway,
                eventBus
        );
    }

    @Bean
    public GetAllScriptsUseCase scriptBean23(ScriptRepositoryGateway scriptRepositoryGateway,
                                             ScriptErrorRepositoryGateway scriptErrorRepositoryGateway) {

        return new GetAllScriptsUseCaseImpl(scriptRepositoryGateway, scriptErrorRepositoryGateway);
    }

    @Bean
    public GetScriptUseCase scriptBean24(ScriptRepositoryGateway scriptRepositoryGateway) {
        return new GetScriptUseCaseImpl(scriptRepositoryGateway);
    }

    @Bean
    public UpdateScriptUseCase scriptBean25(ScriptRepositoryGateway scriptRepositoryGateway,
                                            Locker locker,
                                            TransactionStarter transactionStarter,
                                            EventBus eventBus) {

        return new UpdateScriptUseCaseImpl(scriptRepositoryGateway, transactionStarter, locker, eventBus);
    }

    @Bean
    public CreateScriptUseCase scriptBean26(ScriptRepositoryGateway scriptRepositoryGateway,
                                            Locker locker,
                                            TransactionStarter transactionStarter,
                                            ProjectIdCheckerGateway projectIdCheckerGateway) {

        return new CreateScriptUseCaseImpl(scriptRepositoryGateway, transactionStarter, locker, projectIdCheckerGateway);
    }

    @Bean
    public GetNumberOfErrorsUseCase scriptBean27(ScriptErrorRepositoryGateway scriptErrorRepositoryGateway) {

        return new GetScriptErrorsUseCaseImpl(scriptErrorRepositoryGateway);
    }

    @Bean
    public ClientProviderGateway scriptBean30(MQTTClientManager mqttClientManager, ClientRepositoryGateway clientRepositoryGateway) {
        return new ClientProviderGatewayImpl(mqttClientManager, clientRepositoryGateway);
    }

    @Bean
    public SetDebugEnabledUseCase scriptBean31(SetDebugEnabledGateway setDebugEnabledGateway, Locker locker) {
        return new SetDebugEnabledUseCaseImpl(setDebugEnabledGateway, locker);
    }

    @Bean
    public SetDebugEnabledGateway scriptBean32(@Qualifier("LogCache") gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache) {
        return new SetDebugEnabledGatewayImpl(logCache);
    }

    @Bean
    public IsDebugEnabledGateway scriptBean33(@Qualifier("LogCache") gnoolson.saturday.common.cache.Cache<ScriptId, LogGateway> logCache) {
        return new IsDebugEnabledGatewayImpl(logCache);
    }

    @Bean
    public IsDebugEnabledUseCase scriptBean34(IsDebugEnabledGateway isDebugEnabledGateway) {
        return new IsDebugEnabledUseCaseImpl(isDebugEnabledGateway);
    }

}

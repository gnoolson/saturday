package gnoolson.saturday.app.config;

import gnoolson.saturday.app.plugin.*;
import gnoolson.saturday.lua_script_executor.ScriptLauncherGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class PluginConfig {

    @Bean
    public SaturdayPluginManager pluginBean1(@Value("${gnoolson.saturday.plugin.path}") String path,
                                             ScriptLauncherGateway scriptLauncherGateway) throws FileNotFoundException {

        return PluginLoader.load(path, scriptLauncherGateway);
    }

    @Bean
    public GetContentUseCase pluginBean2(SaturdayPluginManager saturdayPluginManager) {
        return new GetContentUseCaseImpl(saturdayPluginManager);
    }

    @Bean
    public ExecuteActionUseCase pluginBean3(SaturdayPluginManager saturdayPluginManager) {
        return new ExecuteActionUseCaseImpl(saturdayPluginManager);
    }

}

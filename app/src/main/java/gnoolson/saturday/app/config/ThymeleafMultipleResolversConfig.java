package gnoolson.saturday.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class ThymeleafMultipleResolversConfig {

    @Value("${spring.thymeleaf.cache}")
    private boolean cacheable;

    @Value("${spring.thymeleaf.prefix}")
    private String templatePath;

    /*
     *
     *
     * */
    @Bean
    @Primary
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(templateResolver());
        return engine;
    }

    @Bean(name = "stringTemplateEngine")
    public SpringTemplateEngine stringTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setTemplateResolver(stringTemplateResolver());
        return engine;
    }

    /*
     *
     *
     * */
    private UrlTemplateResolver templateResolver() {
        UrlTemplateResolver resolver = new UrlTemplateResolver();
        resolver.setPrefix(templatePath);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(cacheable);
        resolver.setOrder(1);

        return resolver;
    }

    private ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(2);

        return templateResolver;
    }

}
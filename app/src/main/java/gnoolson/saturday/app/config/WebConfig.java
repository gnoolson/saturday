package gnoolson.saturday.app.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private final MessageSource messageSource;

    @Value("${gnoolson.saturday.dashboard.static}")
    private String dashboardStatic;
    @Value("${gnoolson.saturday.web.static.path}")
    private String webStatic;

    /*
     *
     *
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(webStatic).setCacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS));
        registry.addResourceHandler("/dashboard/static/**").addResourceLocations(dashboardStatic).setCacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS));
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setCookieName("LANG");
        resolver.setCookieMaxAge(60 * 60 * 24 * 31);
        resolver.setCookieHttpOnly(true);

        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    @Bean
    public javax.validation.Validator validator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }

    // це щоб працював переклад для валідації
    @Override
    public Validator getValidator() {
        return (LocalValidatorFactoryBean) validator();
    }

    // trim strings in dto
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();

                SimpleModule module = new SimpleModule();
                module.addDeserializer(String.class, new JsonDeserializer<String>() {
                    @Override
                    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                        String value = p.getValueAsString();
                        return value == null ? null : value.trim();
                    }
                });

                mapper.registerModule(module);
            }
        }
    }

}
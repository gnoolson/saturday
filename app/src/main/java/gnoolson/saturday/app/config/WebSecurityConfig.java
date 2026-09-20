package gnoolson.saturday.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // https://bcrypt-generator.com
    // 12 rounds
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /*
     *
     *
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests().antMatchers(
                        "/login",
                        "/",
                        "/static/**",
                        "/dashboard/static/**",
                        "/dashboard/*",
                        "/api/dashboard/*/action",
                        "/api/version",
                        "/api/user/create-editor",
                        "/api/root/**",
                        "/error**"
                ).permitAll()
                .and()

                .authorizeRequests().antMatchers("/**")
                .hasAnyRole("VIEWER", "EDITOR")
                .and()

                .formLogin((form) -> {
                    form.loginPage("/login");
                    form.permitAll();
                })
                .logout((logout) -> logout.logoutUrl("/logout"))
                .exceptionHandling(handler -> handler.defaultAuthenticationEntryPointFor(restAuthenticationEntryPoint(),
                        new AntPathRequestMatcher("/api/**")))
                .logout();

        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }

}

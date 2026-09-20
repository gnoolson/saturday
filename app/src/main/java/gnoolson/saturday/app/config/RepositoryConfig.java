package gnoolson.saturday.app.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;


@EntityScan(basePackages = "gnoolson.saturday.app.repository")
@EnableJpaRepositories(basePackages = {"gnoolson.saturday.app.repository"}) // for Crud
@EnableTransactionManagement
@Configuration
public class RepositoryConfig {

    @Value("${h2.db_server.port}")
    private String h2DBPort;

    @Value("${h2.web_server.port}")
    private String h2WebPort;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.driver_class_name}")
    private String driverClassName;

    /*
     *
     *
     * */
    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "h2DBServer")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2DBPort, "-ifNotExists");
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "h2webSever")
    public Server webServer() throws SQLException {
        return Server.createWebServer("-webPort", h2WebPort);
    }

    @Primary
    @Bean(name = "H2DataSource", destroyMethod = "close")
    @DependsOn("h2DBServer")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

}

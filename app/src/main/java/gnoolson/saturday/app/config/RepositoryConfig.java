package gnoolson.saturday.app.config;

import gnoolson.saturday.app.repository.backup.*;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import org.apache.commons.dbcp.BasicDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
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

    @Bean
    public CreateBackup createBackupBean(@Value("${gnoolson.saturday.h2.backup.path}") String folderStr,
                                         H2Repository h2Repository, TimeProvider timeProvider, TransactionStarter transactionStarter) throws Exception {

        File folder = ResourceUtils.getFile(folderStr);
        return new CreateBackupImpl(folder, h2Repository, timeProvider, transactionStarter);
    }

    @Bean
    public DeleteUnnecessaryBackups deleteUnnecessaryBackupsBean(@Value("${gnoolson.saturday.h2.backup.path}") String folderStr,
                                                                 @Value("${gnoolson.saturday.h2.backup.max_files}") int maxFiles) throws FileNotFoundException {
        File folder = ResourceUtils.getFile(folderStr);
        return new DeleteUnnecessaryBackupsImpl(folder, PositiveNumber.of(maxFiles));
    }

}

package gnoolson.saturday.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty(
                "spring.config.location",
                "file:config/application.properties"
        );

        SpringApplication.run(Application.class, args);
    }

}

package com.talan.polaris;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import static com.talan.polaris.constants.ConfigurationConstants.CONFIGURATION_PROPERTIES_FILE_NAME;

/**
 * Application.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@PropertySource("classpath:" + CONFIGURATION_PROPERTIES_FILE_NAME)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

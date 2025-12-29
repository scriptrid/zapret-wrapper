package ru.scriptrid.zapretwrapper.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan Selikhov
 */
@Configuration
@ConfigurationProperties(prefix = "zapret")
@Setter
@Getter
@Slf4j
public class ZapretConfigurationProperties {

    private String folderPath;
    private String batPath;
    private String batName;

    @PostConstruct
    public void validate() {
        if (folderPath == null || folderPath.isBlank()) {
            log.warn("zapret folder path is null or empty");
        }
        if (batPath == null || batPath.isBlank()) {
            log.warn("zapret bat file path is null or empty");
        }
        if (batName == null || batName.isBlank()) {
            log.warn("zapret bat file name is null or empty");
        }
        log.info("""
                  Params:
                      folderPath = {}
                      batPath = {}
                      batName = {}
                """, folderPath, batPath, batName);
    }

    public boolean folderPathIsSpecified() {
        return folderPath != null && !folderPath.isBlank();
    }

    public boolean batPathIsSpecified() {
        return batPath != null && !batPath.isBlank();
    }

    public boolean batNameIsSpecified() {
        return batName != null && !batName.isBlank();
    }

    @Override
    public String toString() {
        return String.format("""
                  Params:
                      folderPath = %s
                      batPath = %s
                      batName = %s
                """, folderPath, batPath, batName);
    }
}

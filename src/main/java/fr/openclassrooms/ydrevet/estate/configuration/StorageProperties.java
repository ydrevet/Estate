package fr.openclassrooms.ydrevet.estate.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "upload";
}

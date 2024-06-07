package fr.openclassrooms.ydrevet.estate;

import fr.openclassrooms.ydrevet.estate.services.StorageBackend;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EstateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateApplication.class, args);
    }

    @Bean
    CommandLineRunner initStorageBackend(StorageBackend storageBackend) {
        return (args) -> {
            storageBackend.init();
        };
    }
}

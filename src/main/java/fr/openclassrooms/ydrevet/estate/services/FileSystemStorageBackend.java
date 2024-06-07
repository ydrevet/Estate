package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.configuration.StorageProperties;
import fr.openclassrooms.ydrevet.estate.exceptions.FileNotFoundException;
import fr.openclassrooms.ydrevet.estate.exceptions.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageBackend implements StorageBackend {
    private final Path rootLocation;

    public FileSystemStorageBackend(StorageProperties storageProperties) {
        if (storageProperties.getLocation().trim().isEmpty()) {
            throw new StorageException("Aucun répertoire spécifié pour stocker les fichiers uploadés");
        }
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }


    @Override
    public void init() {
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(this.rootLocation);
            } catch (IOException e) {
                throw new StorageException("La création du répertoire de destination a échouée : ", e);
            }
        }
    }

    @Override
    public void store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Fichier vide, abandon.");
        }
        if (file.getOriginalFilename() == null) {
            throw new StorageException("Nom du fichier de destination vide, abandon.");
        }
        try {
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Tentative d’enregistrer le fichier hors du répertoire approprié, abandon.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Échec de l’enregistrement du fichier : ", ex);
        }

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = this.rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Aucun fichier trouvé avec le nom " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Impossible de charger le fichier : " + filename, ex);
        }
    }
}

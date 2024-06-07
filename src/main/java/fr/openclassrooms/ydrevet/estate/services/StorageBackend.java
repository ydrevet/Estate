package fr.openclassrooms.ydrevet.estate.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contrat rempli par les services permettant de stocker des fichiers
 */
public interface StorageBackend {
    /**
     * Initialisation du backend (création d’un bucket, d’un répertoire...)
     */
    void init();

    /**
     * Stockage d’un fichier reçu par un controller
     * @param file Fichier reçu par le controller
     */
    void store(MultipartFile file);

    /**
     * Récupère un fichier précédemment stocké
     * @param filename Nom du fichier à récupérer
     * @return Le fichier récupéré sous forme de resource
     */
    Resource load(String filename);
}

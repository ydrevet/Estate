package fr.openclassrooms.ydrevet.estate.exceptions;

public class RentalNotFoundException extends BaseNotFoundException {
    public RentalNotFoundException(Long id) {
        super("Aucune location avec l’ID " + id);
    }
}

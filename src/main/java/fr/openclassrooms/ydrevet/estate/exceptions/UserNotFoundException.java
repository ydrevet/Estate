package fr.openclassrooms.ydrevet.estate.exceptions;

public class UserNotFoundException extends BaseNotFoundException {
    public UserNotFoundException(Long id) {
        super("Aucun utilisateur trouvé avec l’ID " + id);
    }
}

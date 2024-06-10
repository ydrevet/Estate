package fr.openclassrooms.ydrevet.estate.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String email) {
        super("Un utilisateur est déjà enregistré avec l’addresse " + email);
    }
}

package fr.openclassrooms.ydrevet.estate.exceptions;

public class BaseNotFoundException extends RuntimeException {
    public BaseNotFoundException() {
    }

    public BaseNotFoundException(Long id) {
        super("Aucune ressource trouvée avec l’ID " + id);
    }

    public BaseNotFoundException(String message) {
        super(message);
    }

    public BaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseNotFoundException(Throwable cause) {
        super(cause);
    }

    public BaseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

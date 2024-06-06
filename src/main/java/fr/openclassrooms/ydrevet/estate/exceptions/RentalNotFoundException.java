package fr.openclassrooms.ydrevet.estate.exceptions;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(Long id) {
        super("Aucune location avec l’ID " + id);
    }

    public RentalNotFoundException() {
    }

    public RentalNotFoundException(String message) {
        super(message);
    }

    public RentalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalNotFoundException(Throwable cause) {
        super(cause);
    }

    public RentalNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

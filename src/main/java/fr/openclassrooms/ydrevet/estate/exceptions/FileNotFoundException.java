package fr.openclassrooms.ydrevet.estate.exceptions;

import java.net.MalformedURLException;

public class FileNotFoundException extends BaseNotFoundException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {

    }
}

package fr.openclassrooms.ydrevet.estate.controllers.advices;

import fr.openclassrooms.ydrevet.estate.dto.MessageResponse;
import fr.openclassrooms.ydrevet.estate.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleUserNotFoundException(UserNotFoundException ex) {
        return new MessageResponse(ex.getMessage());
    }
}

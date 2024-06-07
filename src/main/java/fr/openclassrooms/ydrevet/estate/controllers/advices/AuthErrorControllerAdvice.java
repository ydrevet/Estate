package fr.openclassrooms.ydrevet.estate.controllers.advices;

import fr.openclassrooms.ydrevet.estate.dto.MessageResponse;
import fr.openclassrooms.ydrevet.estate.exceptions.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthErrorControllerAdvice {
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse authenticationExceptionHandler(AuthenticationException e) {
        return new MessageResponse(e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResponse unAuthorizedExceptionHandler(UnAuthorizedException e) {
        return new MessageResponse(e.getMessage());
    }

}

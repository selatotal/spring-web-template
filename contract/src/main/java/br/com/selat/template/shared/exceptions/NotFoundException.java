package br.com.selat.template.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(Throwable exception){
        super(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

    public NotFoundException(String message, Throwable exception){
        super(HttpStatus.NOT_FOUND, message, exception);
    }
}

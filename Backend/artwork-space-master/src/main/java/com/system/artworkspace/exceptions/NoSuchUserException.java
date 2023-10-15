package com.system.artworkspace.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException() {
        super();
    }
    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoSuchUserException(String message) {
        super(message);
    }
    public NoSuchUserException(Throwable cause) {
        super(cause);
    }
}

package com.system.artworkspace.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchExhibitionException extends RuntimeException{
    public NoSuchExhibitionException() {
        super();
    }
    public NoSuchExhibitionException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoSuchExhibitionException(String message) {
        super(message);
    }
    public NoSuchExhibitionException(Throwable cause) {
        super(cause);
    }
}

package com.system.artworkspace.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchArtworkException extends RuntimeException {
    public NoSuchArtworkException() {
        super();
    }
    public NoSuchArtworkException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoSuchArtworkException(String message) {
        super(message);
    }
    public NoSuchArtworkException(Throwable cause) {
        super(cause);
    }
}
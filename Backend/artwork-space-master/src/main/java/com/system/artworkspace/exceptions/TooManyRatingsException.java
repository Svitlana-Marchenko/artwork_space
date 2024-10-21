package com.system.artworkspace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TooManyRatingsException extends RuntimeException{

    public TooManyRatingsException() {
        super("Curator can give only one review about the artwork!");
    }

    public TooManyRatingsException(Throwable cause) {
        super("Curator can give only one review about the artwork!",cause);
    }
}

package com.system.artworkspace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchAuctionException extends RuntimeException{

    public NoSuchAuctionException() {
        super();
    }

    public NoSuchAuctionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchAuctionException(String message) {
        super(message);
    }

    public NoSuchAuctionException(Throwable cause) {
        super(cause);
    }
}

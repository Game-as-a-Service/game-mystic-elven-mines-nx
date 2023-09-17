package com.gaas.mystic.elven.exceptions;

public class ElvenGameException extends RuntimeException {

    public ElvenGameException(String message) {
        super(message);
    }

    public ElvenGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElvenGameException(Throwable cause) {
        super(cause);
    }

    protected ElvenGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

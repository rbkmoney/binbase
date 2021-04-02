package com.rbkmoney.binbase.exception;

public class BinNotFoundException extends RuntimeException {

    public BinNotFoundException() {
    }

    public BinNotFoundException(String message) {
        super(message);
    }

    public BinNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinNotFoundException(Throwable cause) {
        super(cause);
    }

    public BinNotFoundException(String message,
                                Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

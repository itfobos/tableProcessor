package com.processor.reader;

public class InDataFormatException extends Exception {
    public InDataFormatException() {
    }

    public InDataFormatException(String message) {
        super(message);
    }

    public InDataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InDataFormatException(Throwable cause) {
        super(cause);
    }

    public InDataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

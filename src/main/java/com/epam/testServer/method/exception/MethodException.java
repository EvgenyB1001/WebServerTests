package com.epam.testServer.method.exception;

/**
 * Created by 777 on 13.04.2017.
 */
public class MethodException extends Exception {

    public MethodException() {
        super();
    }

    public MethodException(String message) {
        super(message);
    }

    public MethodException(String message, Exception cause) {
        super(message, cause);
    }

    public MethodException(Exception cause) {
        super(cause);
    }
}

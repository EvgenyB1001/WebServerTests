package com.epam.testServer.storage.exception;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public class StorageException extends Exception {

    public StorageException() {
        super();
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public StorageException(Exception e) {
        super(e);
    }
}

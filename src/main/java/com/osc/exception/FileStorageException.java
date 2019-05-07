package com.osc.exception;

/**
 * Created by Kerisnarendra on 6/05/2019.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.folk.exception;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super("Could not find " + message);
    }
}

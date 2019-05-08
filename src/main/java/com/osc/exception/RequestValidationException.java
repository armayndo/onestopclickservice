package com.osc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Kerisnarendra on 07/05/2019.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestValidationException extends RuntimeException{
    public RequestValidationException() { }

    public RequestValidationException(String entity) {
        super(entity);
    }
}

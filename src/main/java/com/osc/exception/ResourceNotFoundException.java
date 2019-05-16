package com.osc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() { }

    public ResourceNotFoundException(String entity, Long id) {
        super(entity + " id " + id + " not found");
    }
    
    /*
     * Added by Tommy Toban on 15/05/2019 
     */
    public ResourceNotFoundException(String entity,String field,String value) {
        super(entity+" "+field+" "+value+" not found");
    }
}

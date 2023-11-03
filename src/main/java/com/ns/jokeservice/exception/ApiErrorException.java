package com.ns.jokeservice.exception;

import com.ns.jokeservice.model.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorException extends RuntimeException{

    private ErrorResponse errorResponse;
    public ApiErrorException(String msg, ErrorResponse errorResponse) {
        super(msg);
        this.errorResponse = errorResponse;
    }
}

package com.ns.jokeservice.exception;

import com.ns.jokeservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<String> handleClientErrorException(ClientErrorException ex) {
        String exInfo = ex.getMessage();
        return new ResponseEntity<>(exInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<String> handleServerErrorException(ServerErrorException ex) {
        String exInfo = ex.getMessage();
        return new ResponseEntity<>(exInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErrorResponse> handleApiErrorException(ApiErrorException ex) {
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}

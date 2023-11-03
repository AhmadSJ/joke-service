package com.ns.jokeservice.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private boolean error;
    private boolean internalError;
    private int code;
    private String message;
    private List<String> causedBy;
    private String additionalInfo;
    private long timestamp;

}
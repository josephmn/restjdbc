package com.aws.restjdbc.exception.types;

public class UnknownFieldException extends RuntimeException {
    public UnknownFieldException(String message) {
        super(message);
    }
}

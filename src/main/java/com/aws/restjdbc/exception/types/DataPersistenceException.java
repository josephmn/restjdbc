package com.aws.restjdbc.exception.types;

public class DataPersistenceException extends RuntimeException {
    public DataPersistenceException(String message) {
        super(message);
    }
}

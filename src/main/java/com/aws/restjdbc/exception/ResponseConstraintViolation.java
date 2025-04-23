package com.aws.restjdbc.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseConstraintViolation {

    private long timestamp;
    private int status;
    private String error;
    private Object message;
    private String path;

    public void setPath(String path) {
        this.path = path.replace("uri=", "");
    }
}

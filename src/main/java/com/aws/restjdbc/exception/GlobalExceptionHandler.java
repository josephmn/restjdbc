package com.aws.restjdbc.exception;

import com.aws.restjdbc.exception.types.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.IllegalArgumentException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(SQLSyntaxErrorException.class)
//    public ResponseEntity<ErrorResponse> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
//        Map<Integer, String> errors = new HashMap<>();
//        errors.put(1, "Syntax error in SQL or table not found: " + ex.getMessage());
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Error in Query SQL",
//                new Date(),
//                errors
//        );
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(DataNotContentException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotContentException ex) {
//        Map<Integer, String> errors = new HashMap<>();
//        errors.put(1, "No data available for the requested query");
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                ex.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleDataNotFoundException(IllegalArgumentException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                ex.getMessage(),
//                new Date()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseConstraintViolation> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        ResponseConstraintViolation response = new ResponseConstraintViolation();
        response.setTimestamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        String message = ex.getMessage();
        ArrayList<String> errorMessages = new ArrayList<>(Arrays.asList(message.substring(1, message.length() - 1).split(", ")));
        response.setMessage(errorMessages);
        response.setError("Bad Request");
        response.setPath(webRequest.getDescription(false));
        log.info("Error Illegal Argument - 400");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ResponseConstraintViolation> handleUnknownFieldException(DuplicateFieldException ex, WebRequest webRequest) {
        ResponseConstraintViolation response = new ResponseConstraintViolation();
        response.setTimestamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setError("Bad Request - Unknown Fields");
        response.setPath(webRequest.getDescription(false));
        log.info("Error Duplicate Field - 400");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownFieldException.class)
    public ResponseEntity<ResponseConstraintViolation> handleUnknownFieldException(UnknownFieldException ex, WebRequest webRequest) {
        ResponseConstraintViolation response = new ResponseConstraintViolation();
        response.setTimestamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setError("Bad Request - Unknown Fields");
        response.setPath(webRequest.getDescription(false));
        log.info("Error Unknown Field - 400");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataPersistenceException.class)
    public ResponseEntity<ErrorResponse> handleDataPersistenceException(DataPersistenceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.onlineenergyutilityplatform.exceptions;

import com.onlineenergyutilityplatform.dto.ErrorInformation;
import com.onlineenergyutilityplatform.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * Global exceptions handler
 */
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    /**
     * @param ex exception
     * @return Http response with 400 when entity not found occurs
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                new ErrorInformation(
                        ex.getMessage(),
                        ex.getClass().getCanonicalName())), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new ErrorInformation(ex.getMessage(), ex.getClass().getCanonicalName()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

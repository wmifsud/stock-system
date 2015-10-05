package com.stock.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.TimeoutException;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * Required to Catch the exceptions thrown
 * be the rest controller so that the error message can be extracted.
 */
@ControllerAdvice
public class StockControllerValidation {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return error.getDefaultMessage();
    }

    /**
     * TimeoutException which may be thrown by the gateway
     * in case when the channel takes too long to respond.
     * @param ex {@link TimeoutException}
     * @return {@link String} the error message.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TimeoutException.class)
    public String processValidationError(TimeoutException ex) {
        return ex.getMessage();
    }

    /**
     * Any exception which may be thrown by either the
     * gateway or the database will be caught by this method
     * and a proper description with the error message will be returned.
     * @param ex {@link Exception}
     * @return {@link String} the error message.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String processValidationError(Exception ex) {
        return ex.getClass().getName() + ": " + ex.getMessage();
    }
}

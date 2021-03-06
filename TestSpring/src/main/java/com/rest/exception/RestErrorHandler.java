/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.exception;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.rest.controller.UserController;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import java.util.List;
import java.util.Locale;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Ataur Rahman
 */
@ControllerAdvice(basePackageClasses={UserController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestErrorHandler {
 
    private MessageSource messageSource;
 
    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Errors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
 
        return processFieldErrors(fieldErrors);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String processValidationError(Exception ex) {
        String result = ex.getMessage();
        System.out.println("###########"+result);
        return result;
    }
    
    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Errors processPersistentException(PersistenceException ex) {
        DatabaseException dbex = (DatabaseException)ex.getCause();
        MySQLIntegrityConstraintViolationException b  = (MySQLIntegrityConstraintViolationException)dbex.getCause();
        return Utils.processApiError(b.getLocalizedMessage(), b.getErrorCode());
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public Errors processUserNameNotFoundException(UsernameNotFoundException ex) {
        String result = ex.getMessage();
        System.out.println("###########"+result);
        return Utils.processApiError(result, ErrorCodes.NOT_FOUND);
        //return result;
    }
 
    private Errors processFieldErrors(List<FieldError> fieldErrors) {
        Errors dto = new Errors();
 
        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
 
        return dto;
    }
 
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
 
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
 
        return localizedErrorMessage;
    }
}

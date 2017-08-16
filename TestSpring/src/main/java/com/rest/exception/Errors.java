/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.exception;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ataur Rahman
 */
public class Errors {
 
    private List<FieldErrorDTO> errors = new ArrayList<>();
 
    public Errors() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        errors.add(error);
    }
    
    
 
    //Getter is omitted.

    public List<FieldErrorDTO> getErrors() {
        return errors;
    }

    public void setFieldErrors(FieldErrorDTO fieldError) {
        this.errors.add(fieldError);
    }
    
    public void setInternalErrors(String errorMessge,int code) {
        this.errors.add(new FieldErrorDTO(errorMessge, code));
    }
}

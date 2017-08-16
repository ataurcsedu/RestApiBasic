/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.exception;

/**
 *
 * @author Ataur Rahman
 */
public class FieldErrorDTO {
 
    private String field;
 
    private String message;
    private int code;
 
    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }
    
    public FieldErrorDTO(String message, int code) {
        this.message = message;
        this.code = code;
    }
    
    
 
    //Getters are omitted.

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    
}

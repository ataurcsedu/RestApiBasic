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
public class ErrorMessage {
    private String errorMessage;
    private int code;
    
    public ErrorMessage(){
    }
    
    public ErrorMessage(String errorMsg,int code){
        this.errorMessage= errorMsg;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.exception;

public abstract class BaseException extends Exception {

    
    private int errorCode;
    private String errorMessage;

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public BaseException(String string) {
        super(string);
    }

    public BaseException(String message, int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }


    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return this.errorCode + " : " + this.getErrorMessage();
    }
}

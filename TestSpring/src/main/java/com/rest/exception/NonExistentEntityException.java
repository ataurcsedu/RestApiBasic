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
public class NonExistentEntityException extends Exception{
    
    public NonExistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonExistentEntityException(String message) {
        super(message);
    }
}
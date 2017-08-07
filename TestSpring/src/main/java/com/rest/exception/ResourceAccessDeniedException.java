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
public class ResourceAccessDeniedException extends BaseException{

    public ResourceAccessDeniedException(String messagae, int errorCode) {
        super(messagae, errorCode);
    }
}

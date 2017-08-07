/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.ws.response;

/**
 *
 * @author Ataur Rahman
 */
public class BaseResponse {
    
    OperationResult operationResult = new OperationResult();
    
    
    public BaseResponse(){
        super();
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }
    
    
}

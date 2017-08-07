/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.ws.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ataur Rahman
 */
public class OperationResult {
    
    List<String> errorList = new ArrayList<String>();
    boolean success;
    
    public OperationResult(){
        super();
    }
    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    
}

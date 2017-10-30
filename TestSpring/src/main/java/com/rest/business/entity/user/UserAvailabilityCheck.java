/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.entity.user;

import java.util.List;

/**
 *
 * @author Ataur Rahman
 */
public class UserAvailabilityCheck {
    private String message;
    private List<String> availableUser;
    
    public UserAvailabilityCheck(){
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAvailableUser() {
        return availableUser;
    }

    public void setAvailableUser(List<String> availableUser) {
        this.availableUser = availableUser;
    }
    
    
}

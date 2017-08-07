/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.user.entity.UserBO;
import com.rest.business.user.entity.UserSummary;
import com.rest.ws.response.GetUserServiceResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ataur Rahman
 */

public interface IUserManager {
    
    public GetUserServiceResponse getUsers(Long startIndex,Long limit,UserBO user);
    public UserSummary getUser(String userId);
}

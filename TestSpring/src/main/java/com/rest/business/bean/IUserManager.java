/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.user.entity.UserBO;
import com.rest.business.user.entity.UserSummary;
import com.rest.exception.ServiceException;
import com.rest.ws.response.GetUserServiceResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ataur Rahman
 */

public interface IUserManager {
    
    public Object getUsers(Long startIndex,Long limit,UserBO user);
    public Object createUser(UserBO user,String role);
    public Object updateUser(UserBO user,int id);
    public UserSummary getUser(String userId);
    public UserSummary activateUser(int userId)throws ServiceException;
}

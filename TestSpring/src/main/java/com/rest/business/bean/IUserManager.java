/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.entity.user.UserBO;
import com.rest.business.entity.user.UserSummary;
import com.rest.database.entity.User;
import com.rest.exception.ServiceException;
import com.rest.ws.response.GetUserServiceResponse;
import java.util.List;
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
    public User getUser(String userName,String password);
    public List<String> getUserByName(String userName);
    public UserSummary activateUser(int userId)throws ServiceException;
}

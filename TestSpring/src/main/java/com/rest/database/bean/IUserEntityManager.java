/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.exception.ServiceException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ataur Rahman
 */

public interface IUserEntityManager {
    
    public Object getUsers(int startIndex, int limit, String where) throws ServiceException;
    public Object getUser(String where) throws ServiceException;
    public long getTotalCount(String where);
    
    default public void writeLog(){
        Logger LOGGER = Logger.getLogger("<USER > : ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        if(roles!=null){
            LOGGER.log(Level.INFO,"USER NAME "+ authentication.getName()+" ROLES "+Arrays.toString(roles.toArray()));
        }else{
            LOGGER.log(Level.SEVERE,"NO ROLES FOUND FOR USER "+authentication.getName());
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IUserManager;
import com.rest.business.user.entity.UserBO;
import com.rest.business.user.entity.UserSummary;
import com.rest.exception.ResourceAccessDeniedException;
import com.rest.utils.Defs;
import com.rest.ws.response.GetUserServiceResponse;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author Ataur Rahman
 */

@RestController
@RequestMapping(Defs.CONTROLLER_PATH)

public class UserController {
    
    @Autowired
    private IUserManager userService;
    
    /*
    @Autowired
    public void setIUserManager(IUserManager userService) {
        this.userService = userService;
    }*/
 
    
    @RequestMapping(value = "users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserSummary> getUsers(SecurityContextHolderAwareRequestWrapper s, HttpServletRequest req,@RequestParam(value = "offset", defaultValue = "0") long index,
			@RequestParam(value = "limit", defaultValue = "10") long limit) throws ResourceAccessDeniedException {
        
        //if(req.isUserInRole("ROLE_USER")){
            GetUserServiceResponse resp = new GetUserServiceResponse();
            resp = userService.getUsers(index, limit, new UserBO());
            return resp.getUserList();
        //}
        
    }

    
    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserSummary getUser(HttpServletRequest req,@PathVariable String id) {
        UserSummary userSummary = userService.getUser(id);
        return userSummary;
        
    }
    
}

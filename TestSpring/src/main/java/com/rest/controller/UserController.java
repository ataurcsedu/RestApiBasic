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
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import com.rest.ws.response.GetUserServiceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ataur Rahman
 * 
 */
@RestController
@RequestMapping(Defs.CONTROLLER_PATH)

public class UserController {

    @Autowired
    private IUserManager userService;

    
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public Object getUsers(SecurityContextHolderAwareRequestWrapper s, HttpServletRequest req, @RequestParam(value = "offset", defaultValue = "0") long index,
            @RequestParam(value = "limit", defaultValue = "10") long limit) throws ResourceAccessDeniedException {

        //if(req.isUserInRole("ROLE_USER")){
        GetUserServiceResponse resp = new GetUserServiceResponse();
        Object object = userService.getUsers(index, limit, new UserBO());
        return object;
    }

    
    @RequestMapping(value = "users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createUsers(HttpServletRequest req,HttpServletResponse response, @ModelAttribute @Valid UserBO userBO, BindingResult result) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        //if roles field is empty the default user created with ROLE_USER
        return userService.createUser(userBO,Defs.ROLE_USER);
    }
    
    
    @RequestMapping(value = "users/{userid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object updateUsers(HttpServletRequest req,HttpServletResponse response, @RequestBody UserBO userBO, BindingResult result,
            @PathVariable String userid) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        if(userid!=null){
            return userService.updateUser(userBO,Integer.parseInt(userid));
        }
        return null;
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public UserSummary getUser(HttpServletRequest req, @PathVariable String id) {
        UserSummary userSummary = userService.getUser(id);
        return userSummary;

    }

    @RequestMapping(value = "users/{id}/activate", method = RequestMethod.GET)
    public Object activateUser(HttpServletRequest req, @PathVariable String id) {
        if(!Utils.isEmpty(id)){
            UserSummary userSummary;
            try {
                userSummary = userService.activateUser(Integer.parseInt(id));
            } catch (ServiceException ex) {
                return Utils.processApiError(ex.getMessage(), ex.getErrorCode());
            }
            return userSummary;
        }
        return Utils.processApiError("No user id Found", ErrorCodes.UPDATE);

    }

}

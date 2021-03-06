/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IUserManager;
import com.rest.business.entity.user.UserAvailabilityCheck;
import com.rest.business.entity.user.UserBO;
import com.rest.business.entity.user.UserName;
import com.rest.business.entity.user.UserSummary;
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import com.rest.ws.response.GetUserServiceResponse;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",
            value = "Bearer access_token",
            required = true,
            dataType = "string",
            paramType = "header"),
    })*/
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public Object getUsers(SecurityContextHolderAwareRequestWrapper s, HttpServletRequest req, @RequestParam(value = "offset", defaultValue = "0") long index,
            @RequestParam(value = "limit", defaultValue = "10") long limit)  {

        //if(req.isUserInRole("ROLE_USER")){
        GetUserServiceResponse resp = new GetUserServiceResponse();
        Object object = userService.getUsers(index, limit, new UserBO());
        return object;
    }

    
    @ApiOperation(value = "Create a new user", notes = "It creates a new user, but still this state is INACTIVE, to activate you need to invoke activate url")
    @RequestMapping(value = "users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createUsers(HttpServletRequest req,HttpServletResponse response, @RequestBody @Valid UserBO userBO, BindingResult result) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        //if roles field is empty the default user created with ROLE_USER
        return userService.createUser(userBO,Defs.ROLE_USER);
    }
    
    
    @ApiOperation(value = "Update a user by user_id", notes = "It will update a user by user_id")
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
    
    @ApiOperation(value = "User name availability check", notes = "It will check if a user name is exist in database, if exist then return some available user name")
    @RequestMapping(value = "users/check-user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object checkUserAvailable(HttpServletRequest req,@RequestBody UserName userName) {
        if(userName==null){
            return Utils.processApiError("No user name provided in the request", ErrorCodes.NOT_FOUND);
        }
        UserAvailabilityCheck uCheck = new UserAvailabilityCheck();
        if(userName!=null && userName.getUserName().length() > 0 && Utils.isUserNameValid(userName.getUserName())){
            
            List<String> userList = userService.getUserByName(userName.getUserName());
            if(userList!=null && userList.size() > 0){
                for (String u : userList) {
                    if(u.toUpperCase().equalsIgnoreCase(userName.getUserName().toUpperCase())){
                        uCheck.setMessage("User name already exist.");
                        List<String>tmpUserList = new ArrayList<String>();
                        while(tmpUserList!=null){
                            tmpUserList = Utils.generateFourUserName(userName.getUserName());
                            tmpUserList.removeAll(userList);
                            if(tmpUserList!=null && tmpUserList.size() > 0)
                                break;
                        }
                        uCheck.setAvailableUser(tmpUserList);
                    }
                }
                return uCheck;
            }else{
                uCheck.setMessage("User name available");
                uCheck.setAvailableUser(null);
                return uCheck;
            }
            
        }
        return Utils.processApiError("Provided user name not valid", ErrorCodes.INVALID);
    }

    @ApiOperation(value = "Get a Single user by user_id", notes = "It Get a Single user by user_id", response = UserSummary.class)
    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public UserSummary getUser(HttpServletRequest req, @PathVariable String id) {
        UserSummary userSummary = userService.getUser(id);
        return userSummary;

    }

    @ApiOperation(value = "Activate a user for given id", notes = "It activate a new user with provided user_id in the path")
    @RequestMapping(value = "users/{id}/activate", method = RequestMethod.GET)
    public Object activateUser(HttpServletRequest req, @PathVariable String id) {
        if(!Utils.isEmpty(id)){
            UserSummary userSummary;
            try {
                userSummary = userService.activateUser(Integer.parseInt(id));
            } catch (ServiceException ex) {
                return Utils.processApiError(ex.getErrorMessage(), ex.getErrorCode());
            }
            return userSummary;
        }
        return Utils.processApiError("No user id Found", ErrorCodes.UPDATE);

    }

}

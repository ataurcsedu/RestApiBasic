/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IHouseManager;
import com.rest.business.bean.IUserManager;
import com.rest.business.entity.house.HouseBO;
import com.rest.business.entity.house.HouseSummary;
import com.rest.database.entity.User;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author Ataur Rahman
 */
@RestController
@RequestMapping(Defs.CONTROLLER_PATH)

public class HouseController {

    @Autowired
    IHouseManager houseService;
    
    @Autowired
    private IUserManager userService;
    
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("createdBy", "creationDate", "lastUpdatedBy","lastUpdatedDate"); 
    }
    
    @RequestMapping(value = "house", method = RequestMethod.GET)
    public Object getHouse(HttpServletRequest req,@RequestParam(value = "offset", defaultValue = "0") long index,
            @RequestParam(value = "limit", defaultValue = "10") long limit,
            HttpServletResponse response, @ModelAttribute HouseSummary house, BindingResult result) {
        
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        Object object = houseService.getHouseByCriteria(index, limit, house);
        UserDetails u = Utils.getCurrentUserDetailsObject();
        return object;
    }
    
    @RequestMapping(value = "house-created-by-user", method = RequestMethod.GET)
    public Object getHouseCreatedByUser(HttpServletRequest req , HttpServletResponse response) {
        Object object = houseService.getHouseCreatedByUser();
        return object;
    }
    
    
    
    @RequestMapping(value = "house/{id}", method = RequestMethod.GET)
    public Object getHouse(HttpServletRequest req,
            HttpServletResponse response,@PathVariable String id) {
        if(!Utils.isEmpty(id)){
            Object object = houseService.getHouseById(Integer.parseInt(id));
            return object;
        }
        return Utils.processApiError("No house ID provided.", ErrorCodes.GET);
    }
    
    @RequestMapping(value = "house", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createHouse(HttpServletRequest req,HttpServletResponse response, @ModelAttribute @Valid HouseBO house, BindingResult result) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        UserDetails ud = Utils.getCurrentUserDetailsObject();
        if(ud!=null){
            User u = userService.getUser(ud.getUsername(), ud.getPassword());
        }
        
        //if roles field is empty the default user created with ROLE_USER
        return houseService.createHouse(house,Defs.ROLE_USER);
    }
    
    @RequestMapping(value = "house/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object updateHouse(HttpServletRequest req,HttpServletResponse response, @RequestBody HouseBO houseBO, BindingResult result,
            @PathVariable String id) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        UserDetails ud = Utils.getCurrentUserDetailsObject();
        User u = null;
        if(ud!=null){
            u = userService.getUser(ud.getUsername(), ud.getPassword());
        }
        
        if(u.getId()!=null && u.getId().intValue() > 0){
            try{
                return houseService.updateHouse(houseBO,u.getId(),Integer.parseInt(id));
            }catch(Exception e){
                return Utils.processApiError("Invalid user id or house", ErrorCodes.INVALID);
            }
        }
        return Utils.processApiError("No associated House Found.",ErrorCodes.GET);
    }

}

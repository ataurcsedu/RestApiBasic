/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IHouseManager;
import com.rest.business.house.entity.HouseBO;
import com.rest.business.house.entity.HouseSummary;
import com.rest.business.user.entity.UserBO;
import com.rest.utils.Defs;
import com.rest.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Ataur Rahman
 */
@RestController
@RequestMapping(Defs.CONTROLLER_PATH)
public class HouseController {

    @Autowired
    IHouseManager houseService;
    
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("createdBy", "creationDate", "lastUpdatedBy","lastUpdatedDate"); 
    }
    
    @RequestMapping(value = "house", method = RequestMethod.GET)
    @ResponseBody
    public Object getHouse(HttpServletRequest req,@RequestParam(value = "offset", defaultValue = "0") long index,
            @RequestParam(value = "limit", defaultValue = "10") long limit,
            HttpServletResponse response, @ModelAttribute HouseBO house, BindingResult result) {
        
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        Object object = houseService.getHouseByCriteria(index, limit, house);
        return object;
    }
    
    
    
    @RequestMapping(value = "house/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getHouse(HttpServletRequest req,
            HttpServletResponse response,@PathVariable String id) {
        if(!Utils.isEmpty(id)){
            Object object = houseService.getHouseById(Integer.parseInt(id));
            return object;
        }
        return Utils.processApiError("No house ID provided.", Defs.ERROR_CODE_GET);
    }
    
    @RequestMapping(value = "house", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object createUsers(HttpServletRequest req,HttpServletResponse response, @ModelAttribute @Valid HouseBO house, BindingResult result) {
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        //if roles field is empty the default user created with ROLE_USER
        return houseService.createHouse(house,Defs.ROLE_USER);
    }

}

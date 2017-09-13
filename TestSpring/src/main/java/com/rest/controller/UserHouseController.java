/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IHouseManager;
import com.rest.business.bean.IUserManager;
import com.rest.business.house.entity.UserHouse;
import com.rest.business.house.entity.UserHouseBO;
import com.rest.database.entity.User;
import com.rest.utils.Defs;
import com.rest.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ataur Rahman
 */
@RestController
@RequestMapping(Defs.CONTROLLER_PATH)
public class UserHouseController {

    @Autowired
    IHouseManager houseService;

    @Autowired
    private IUserManager userService;

    @RequestMapping(value = "publishAdd/{houseId}", method = RequestMethod.POST)
    @ResponseBody
    public Object createAdd(HttpServletRequest req,
            HttpServletResponse response, @PathVariable String houseId) {

        Object  obj = null;
        if (!Utils.isEmpty(houseId)) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String pass = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            
            
            if (user != null) {
                User u = userService.getUser(user.getUsername(), user.getPassword());
                if(u!=null && u.getId()!=null){
                    obj = houseService.publishedAdd(u, houseId);
                    if(obj instanceof UserHouse){
                        UserHouse uh = (UserHouse)obj;
                        UserHouseBO ub = new UserHouseBO();
                        ub.setId(uh.getId());
                        ub.setHouseId(uh.getHouseId().getId());
                        ub.setUserId(uh.getUserId().getId());
                        ub.setApprovedBy(uh.getApprovedBy());
                        ub.setApproveDate(uh.getApproveDate());
                        ub.setStatus(uh.getStatus());
                        ub.setPriority(uh.getPriority());
                        return ub;
                    }
                }
                
            }
        }
        return Utils.processApiError("No user found for this operation.", 404);
    }

    
    
    
    
}

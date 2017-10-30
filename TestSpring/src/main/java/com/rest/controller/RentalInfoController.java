/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import com.rest.business.bean.IRentalHouseManager;
import com.rest.business.entity.house.HouseSummary;
import com.rest.business.entity.rentalhouse.RentalHouseSummary;
import com.rest.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author Ataur Rahman
 */

@RestController
@RequestMapping("/rent/")

public class RentalInfoController {
    
    @Autowired
    IRentalHouseManager rentalManager;
    
    @RequestMapping(value = "house", method = RequestMethod.GET)
    @ResponseBody
    public Object getRentalInfo(HttpServletRequest req,@RequestParam(value = "offset", defaultValue = "0") long index,
            @RequestParam(value = "limit", defaultValue = "6") long limit,
            HttpServletResponse response, @ModelAttribute HouseSummary house, BindingResult result) {
        
        if(result.hasErrors()){
            return Utils.processApiError(result.getFieldErrors(),response);
        }
        
        /*try {
            // this is for test
            LogMessageProducer.sendMessage();
        } catch (JMSException ex) {
            Logger.getLogger(RentalInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        
        
        Object object = rentalManager.getRentalInfoByCriteria(index, limit, house);
        if(object instanceof RentalHouseSummary){
            RentalHouseSummary h = (RentalHouseSummary)object;
            return h;
        }else return object;
    }
}

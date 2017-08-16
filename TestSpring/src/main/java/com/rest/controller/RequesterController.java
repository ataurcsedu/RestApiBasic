/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Ataur Rahman
 */
@RestController
public class RequesterController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView getUser(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("index");
        return mv;

    }

}

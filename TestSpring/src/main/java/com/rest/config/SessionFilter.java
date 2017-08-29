/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ataur Rahman
 */
public class SessionFilter implements Filter{
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
//        String url = request.getRequestURI();
        
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate,private,max-age=0");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);        

        boolean foundInURL_MENU = false;
        boolean menuPassed = false;
        

        if(!response.containsHeader("X-FRAME-OPTIONS"))
            response.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
        
        
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}

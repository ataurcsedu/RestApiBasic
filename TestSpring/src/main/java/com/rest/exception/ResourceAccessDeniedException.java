/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.exception;

import com.rest.utils.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 *
 * @author Ataur Rahman
 */
public class ResourceAccessDeniedException extends OAuth2AccessDeniedHandler{

    
    public ResourceAccessDeniedException(){
        super();
    }
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        System.out.println("You are not authorized");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Ataur Rahman
 */
public class Test {

  public static void main(String[] args) {

	int i = 0;
	while (i < 10) {
		String password = "munna";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);

                System.out.println(hashedPassword);
                System.out.println(passwordEncoder.matches(password, hashedPassword));
                
		i++;
	}

  }
}

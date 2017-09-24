/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import com.rest.database.bean.BaseEntityManager;
import com.rest.database.entity.UsersInfo;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Ataur Rahman
 */
public class Test extends BaseEntityManager {

  public static void main(String[] args) {

	int i = 0;
	while (i < 10) {
		String password = "sun";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);

                System.out.println(hashedPassword);
                System.out.println(passwordEncoder.matches(password, hashedPassword));
                
		i++;
	}
      
    Calendar next = Calendar.getInstance();
        //next.clear();
        next.set(YEAR, next.get(YEAR));
        next.set(MONTH, next.get(MONTH) + 1);
        next.set(DAY_OF_MONTH, 1); // optional, default: 1, our need
        System.out.println(next.getTime());
   
        Test t = new Test();
        Integer result = Integer.MAX_VALUE + Integer.MAX_VALUE;
        System.out.print(Integer.MAX_VALUE);
        //t.op();
  }
  public void op(){
      try{
        EntityManagerFactory em = getEntityManagerFactory();
        
        EntityManager e = em.createEntityManager();
        e.getTransaction().begin();
        Query q = e.createQuery("SELECT u from UsersInfo u where u.username=:username");
        q.setParameter("username", "naveen");
        
        Object o = q.getSingleResult();
        
        UsersInfo u = e.getReference(UsersInfo.class, "a");
        u.setPassword("honululu");
        
        e.merge(u);
        e.getTransaction().commit();
          System.out.println("dsfs"+u);
      } catch (Throwable t) {
          t.printStackTrace();
      }
  }
  
  
  
}

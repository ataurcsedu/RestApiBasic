/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.exception.ServiceException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.eclipse.persistence.config.CacheUsage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 *
 * @author Ataur Rahman
 */
@Service
public class UserEntityManagerBean extends BaseEntityManager implements IUserEntityManager{
    private EntityManagerFactory emf = null;

    private static final Logger LOGGER = Logger.getLogger("<USER > : ");
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public UserEntityManagerBean(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public UserEntityManagerBean() throws ServiceException {
        try {
            this.emf = getEntityManagerFactory();
        } catch (Throwable ex) {
            throw new ServiceException(ex.getMessage(),9999);
        }
    }
    
    @Override
    public Object getUsers(int startIndex, int limit, String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "select u.id, u.userName, u.email, u.status from User u where " + where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createQuery(query);
        
        
        writeLog();
        
        
        return selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).setFirstResult(startIndex).setMaxResults(limit).getResultList();
    }

    
    @Override
    public Object getUser(String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "select u.id, u.userName, u.email, u.status from User u where " + where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createQuery(query);
        
        writeLog();
        
        return selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).getResultList();
    }
    
    @Override
    public long getTotalCount(String where) {
        EntityManager em = null;
        where += "1=1 ";

        em = getEntityManager();
        String query = "select count(u.id) from User u where " + where;
        System.out.println("query=" + query);
        Query selectQuery = em.createQuery(query);
        return ((Long) selectQuery.getSingleResult());
    }
    
    /*@Override
    public void writeLog(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        if(roles!=null){
            LOGGER.log(Level.INFO,"USER NAME "+ authentication.getName()+" ROLES "+Arrays.toString(roles.toArray()));
        }else{
            LOGGER.log(Level.SEVERE,"NO ROLES FOUND FOR USER "+authentication.getName());
        }
        
    }*/
    
    
}

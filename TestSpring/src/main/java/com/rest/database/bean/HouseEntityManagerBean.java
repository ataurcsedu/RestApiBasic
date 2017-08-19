/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.database.entity.House;
import com.rest.database.entity.User;
import com.rest.exception.NonExistentEntityException;
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.Utils;
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
public class HouseEntityManagerBean extends BaseEntityManager implements IHouseEntityManager {

    private EntityManagerFactory emf = null;

    private static final Logger LOGGER = Logger.getLogger("<USER > : ");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HouseEntityManagerBean(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public HouseEntityManagerBean() throws ServiceException {
        try {
            this.emf = getEntityManagerFactory();
        } catch (Throwable ex) {
            throw new ServiceException(ex.getMessage(), 9999);
        }
    }

    @Override
    public Object getHouseByCriteria(int startIndex, int limit, String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "select h.id, h.mobile, h.email, h.username, h.fullname, h.dob,h.code,h.sex, h.status from House h where " + where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createQuery(query);

        writeLog();

        return selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).setFirstResult(startIndex).setMaxResults(limit).getResultList();
    }

    
    @Override
    public Object createHouse(House house) {
        EntityManager em = null;
        em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(house);
            em.flush();
            /*
            // update user role table also, as per Spring security authority check. [ROLE BASED CHECKED]
            com.rest.oauth2.entity.UserRoles userRoles = new com.rest.oauth2.entity.UserRoles();
            userRoles.setUser(user);
            userRoles.setUsername(user.getUsername());
            userRoles.setRole(role);
            em.persist(userRoles);*/
            
            em.getTransaction().commit();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            return new ServiceException(t.getMessage(), 1000);
        } finally {
            em.clear();
            writeLog();
            
        }
        return house;
    }
    
    

    /*@Override
    public Object updateUser(User user) {
        EntityManager em = null;
        em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (NonExistentEntityException ne) {
            em.getTransaction().rollback();
            return new ServiceException(ne.getMessage(), Defs.ERROR_CODE_UPDATE);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            return new ServiceException(t.getMessage(), Defs.ERROR_CODE_UPDATE);
        } finally {
            em.clear();
            writeLog();
        }

        return user;
    }*/



    @Override
    public Object findOne(int id) throws ServiceException {
        EntityManager em = null;
        House house = null;
        try {
            em = getEntityManager();
            house =  em.find(House.class, id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), Defs.ERROR_CODE_GET);
        } finally {
            if (em != null) {
                em.clear();
            }
        }
        return house;

    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.rest.database.entity.User;
import com.rest.exception.NonExistentEntityException;
import com.rest.exception.ServiceException;
import com.rest.utils.ErrorCodes;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */
@Service
public class UserEntityManagerBean extends BaseEntityManager implements IUserEntityManager {

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
            throw new ServiceException(ex.getMessage(), 9999);
        }
    }

    
    
    
    @Override
    public <T> T findById(Class<T> entityClass, Object id) {
        EntityManager em = null;
        em = getEntityManager();
        return em.find(entityClass, id);
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object id) {
        try {
            EntityManager em = null;
            em = getEntityManager();
            return em.getReference(entityClass, id);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
    @Override
    public Object getUsers(int startIndex, int limit, String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "select u.id, u.mobile, u.email, u.username, u.fullname, u.dob,u.code,u.sex, u.status from User u where " + where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createQuery(query);

        writeLog();

        return selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).setFirstResult(startIndex).setMaxResults(limit).getResultList();
    }

    @Override
    public User getUser(String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "select u.id, u.mobile, u.email, u.username, u.fullname, u.dob,u.code, u.sex, u.status from user u where " + where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createNativeQuery(query,User.class);

        writeLog();
        List<User> userListEO = selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).getResultList();
        if(userListEO != null && userListEO.size() >0) {
            return userListEO.get(0);
        }
        
        return null;
    }

    @Override
    public Object createUser(User user,String role) {
        EntityManager em = null;
        em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.flush();
            
            // update user role table also, as per Spring security authority check. [ROLE BASED CHECKED]
            com.rest.oauth2.entity.UserRoles userRoles = new com.rest.oauth2.entity.UserRoles();
            userRoles.setUser(user);
            userRoles.setUsername(user.getUsername());
            userRoles.setRole(role);
            
            em.persist(userRoles);
            em.getTransaction().commit();
        }catch(javax.persistence.PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        }
        catch (Throwable t) {
            em.getTransaction().rollback();
            return new ServiceException(t.getMessage(), 1000);
        } finally {
            em.clear();
            writeLog();
            
        }
        return user;
    }
    
    

    @Override
    public Object updateUser(User user) {
        EntityManager em = null;
        em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (NonExistentEntityException ne) {
            em.getTransaction().rollback();
            return new ServiceException(ne.getMessage(), ErrorCodes.UPDATE);
        } catch (PersistenceException p){
            em.getTransaction().rollback();
            throw p;
        } 
        catch (Throwable t) {
            em.getTransaction().rollback();
            return new ServiceException(t.getMessage(), ErrorCodes.UPDATE);
        } finally {
            em.clear();
            writeLog();
        }

        return user;
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

    @Override
    public Object findOne(int id) throws ServiceException {
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            user =  em.find(User.class, id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCodes.GET);
        } finally {
            if (em != null) {
                em.clear();
            }
        }
        return user;

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

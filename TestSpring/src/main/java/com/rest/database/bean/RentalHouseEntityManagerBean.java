/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.exception.ServiceException;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.eclipse.persistence.config.CacheUsage;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */

@Service
public class RentalHouseEntityManagerBean extends BaseEntityManager implements IRentalHouseEntityManager{
    
    private EntityManagerFactory emf = null;

    private static final Logger LOGGER = Logger.getLogger("<RENT > : ");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     public RentalHouseEntityManagerBean(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RentalHouseEntityManagerBean() throws ServiceException {
        try {
            this.emf = getEntityManagerFactory();
        } catch (Throwable ex) {
            throw new ServiceException(ex.getMessage(), 9999);
        }
    }
    
    
    
    @Override
    public Object getRentalInfoByCriteria(int startIndex, int limit, String where) {
        EntityManager em = null;
        em = getEntityManager();
        where += " 1=1";

        String query = "SELECT "
                            +"u.id, u.fullname, u.mobile, h.area, h.road_no, h.house_no, h.rent_type," 
                            +"h.person_count, h.rent_cost, h.description, h.fromDate"
                        +" FROM user_house uh " +
                            "INNER JOIN house h ON uh.house_id = h.id " +
                            "INNER JOIN user u ON uh.user_id = u.id where "+where;
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createNativeQuery(query);

        return selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).setFirstResult(startIndex).setMaxResults(limit).getResultList();
    }
}

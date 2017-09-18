/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.database.entity.UserHouse;
import com.rest.exception.ServiceException;
import java.util.List;
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
    
    @Override
    public Object getMinimumOneHouseByUserId(Integer userId, Integer houseId) {
        EntityManager em = null;
        em = getEntityManager();
        

        String query = "SELECT h FROM UserHouse h where h.userId =:user_id and h.houseId =:house_id";
        // later add where status = ACTIVE
        System.out.println("QUERY= " + query);
        Query selectQuery = em.createQuery(query,UserHouse.class);
        com.rest.database.entity.User user = new com.rest.database.entity.User();
        com.rest.database.entity.House house = new com.rest.database.entity.House();
        user.setId(userId);
        house.setId(houseId);
        selectQuery.setParameter("user_id", user);
        selectQuery.setParameter("house_id", house);
        List <UserHouse> userHouseList = selectQuery.setHint(CacheUsage.NoCache, CacheUsage.DoNotCheckCache).getResultList();
        if(userHouseList!=null && userHouseList.size() > 0){
            return userHouseList.get(0);
        }else 
            return null;
        
    }
}

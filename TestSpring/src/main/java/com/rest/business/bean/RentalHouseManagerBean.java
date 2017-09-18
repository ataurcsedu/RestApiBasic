/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.house.entity.HouseSummary;
import com.rest.business.rentalhouse.entity.RentalHouseSummary;
import com.rest.database.bean.IRentalHouseEntityManager;
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */

@Service
public class RentalHouseManagerBean implements IRentalHouseManager {

    @Autowired
    IRentalHouseEntityManager rentalService;
    
    @Override
    public Object getRentalInfoByCriteria(Long startIndex, Long limit, HouseSummary houseBO) {
        List<RentalHouseSummary> houseList = new ArrayList<RentalHouseSummary>();
        try {
            if (startIndex == null || Utils.compareLong(startIndex, 0L)) {
                startIndex = 0L;
            }
            if (limit == null || Utils.compareLong(limit, 0L)) {
                limit = 50L;
            }

            //UserEntityManagerBean umb = new UserEntityManagerBean();
            String where = "";

            where += Utils.buildJPQLLikeQuery("h.area", houseBO.getArea());
            where += Utils.buildJPQLLikeQuery("h.road_no", houseBO.getRoadNo());
            where += Utils.buildJPQLLikeQuery("h.house_no", houseBO.getHouseNo());
            if(houseBO.getRentType() > 0){
                where += Utils.buildEqualQuery("h.rent_type", String.valueOf(houseBO.getRentType()));
            }
            if(houseBO.getPersonCount() > 0){
                where += Utils.buildLessThanOrEqualQuery("h.person_count", String.valueOf(houseBO.getPersonCount()));
            }
            if(houseBO.getRentCost() > 0){
                where += Utils.buildLessThanOrEqualQuery("h.rent_cost", String.valueOf(houseBO.getRentCost()));
            }
            if(!Utils.isEmpty(houseBO.getFromDate())){
                where += Utils.buildLessThanOrEqualDateQuery("h.fromDate", houseBO.getFromDate());
            }

            Object object = rentalService.getRentalInfoByCriteria(startIndex.intValue(), limit.intValue(), where);

            if (object != null) {
                List<Object> list = (List<Object>) object;
                for (Object obj : list) {
                    Object[] objArr = (Object[]) obj;
                    RentalHouseSummary house = new RentalHouseSummary();

                    house.setUserId((Integer) objArr[0]);
                    house.setFullName((String) objArr[1]);
                    house.setMobile((String) objArr[2]);
                    house.setArea((String) objArr[3]);
                    house.setRoadNo((String) objArr[4]);
                    house.setHouseNo((String) objArr[5]);
                    house.setRentType((Integer) objArr[6]);
                    house.setPersonCount((Integer) objArr[7]);
                    house.setRentCost((Integer) objArr[8]);
                    house.setDescription((String) objArr[9]);
                    Date date = (Date)objArr[10];
                    house.setFromDate(Utils.getDateToString(date));
                    
                    houseList.add(house);
                }
            }

        } catch (ServiceException se) {
            return Utils.processErrorMessage(se.getErrorMessage(), ErrorCodes.GET);
        } catch (Throwable t) {
            return Utils.processErrorMessage(t.getMessage(), ErrorCodes.GET);
        }
        return houseList;
    }
    
    @Override
    public Object getMinimumOneHouseByUserId(Integer userId, Integer houseId){
        if(userId != null && houseId != null){
            return rentalService.getMinimumOneHouseByUserId(userId, houseId);
        }
        return null;
    }
    
}

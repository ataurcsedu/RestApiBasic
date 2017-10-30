/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.entity.house.HouseBO;
import com.rest.business.entity.house.HouseSummary;
import com.rest.database.bean.IHouseEntityManager;
import com.rest.database.bean.IRentalHouseEntityManager;
import com.rest.database.entity.House;
import com.rest.database.entity.User;
import com.rest.database.entity.UserHouse;
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */
@Service
public class HouseManagerBean implements IHouseManager {

    @Autowired
    IHouseEntityManager houseEntityService;

    @Autowired
    IRentalHouseEntityManager rentalService;

    @Override
    public Object getHouseByCriteria(Long startIndex, Long limit, HouseSummary houseBO) {

        List<HouseSummary> houseList = new ArrayList<HouseSummary>();
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
            if (houseBO.getRentType() > 0) {
                where += Utils.buildEqualQuery("h.rent_type", String.valueOf(houseBO.getRentType()));
            }
            if (houseBO.getPersonCount() > 0) {
                where += Utils.buildLessThanOrEqualQuery("h.person_count", String.valueOf(houseBO.getPersonCount()));
            }
            if (houseBO.getRentCost() > 0) {
                where += Utils.buildLessThanOrEqualQuery("h.rent_cost", String.valueOf(houseBO.getRentCost()));
            }
            if (!Utils.isEmpty(houseBO.getFromDate())) {
                where += Utils.buildLessThanOrEqualDateQuery("h.fromDate", houseBO.getFromDate());
            }

            Object object = houseEntityService.getHouseByCriteria(startIndex.intValue(), limit.intValue(), where);

            if (object != null) {
                List<Object> list = (List<Object>) object;
                for (Object obj : list) {
                    Object[] objArr = (Object[]) obj;
                    HouseSummary house = new HouseSummary();

                    house.setId((Integer) objArr[0]);
                    house.setArea((String) objArr[1]);
                    house.setRoadNo((String) objArr[2]);
                    house.setHouseNo((String) objArr[3]);
                    house.setRentType((Integer) objArr[4]);
                    house.setPersonCount((Integer) objArr[5]);
                    house.setRentCost((Integer) objArr[6]);
                    house.setDescription((String) objArr[7]);
                    Date date = (Date) objArr[8];
                    house.setFromDate(Utils.getDateToString(date));
                    house.setPublished((Integer) objArr[9]);

                    houseList.add(house);
                }
            }

        } catch (ServiceException se) {
            return Utils.processApiError(se.getErrorMessage(), ErrorCodes.GET);
        } catch (Throwable t) {
            return Utils.processApiError(t.getMessage(), ErrorCodes.GET);
        }
        return houseList;
    }

    @Override
    public Object getHouseById(int houseId) {

        HouseSummary house = new HouseSummary();
        try {

            House houseEO = houseEntityService.findOne(houseId);

            if (houseEO != null) {

                house.setId(houseEO.getId());
                house.setArea(houseEO.getArea());
                house.setRoadNo(houseEO.getRoadNo());
                house.setHouseNo(houseEO.getHouseNo());
                house.setRentType(houseEO.getRentType());
                house.setPersonCount(houseEO.getPersonCount());
                house.setRentCost(houseEO.getRentCost());
                house.setDescription(houseEO.getDescription());
                house.setFromDate(Utils.getDateToString(houseEO.getFromDate()));
                house.setPublished(houseEO.getPublished());

            }

        } catch (ServiceException se) {
            return Utils.processApiError(se.getErrorMessage(), ErrorCodes.GET);
        } catch (Throwable t) {
            return Utils.processApiError(t.getMessage(), ErrorCodes.GET);
        }
        return house;
    }

    @Override
    public Object getHouseCreatedByUser() {

        HouseSummary house = new HouseSummary();
        List<HouseSummary> houseList = new ArrayList <HouseSummary>();
        String userName = Utils.getCurrentUser();
        try {

            List<House> hList = houseEntityService.getHouseCreatedByUser(userName);

            if (houseList != null && hList.size() > 0) {
                for (House h : hList) {
                    house.setId(h.getId());
                    house.setArea(h.getArea());
                    house.setRoadNo(h.getRoadNo());
                    house.setHouseNo(h.getHouseNo());
                    house.setRentType(h.getRentType());
                    house.setPersonCount(h.getPersonCount());
                    house.setRentCost(h.getRentCost());
                    house.setDescription(h.getDescription());
                    house.setFromDate(Utils.getDateToString(h.getFromDate()));
                    house.setPublished(h.getPublished());
                    houseList.add(house);
                }

            }

        } catch (ServiceException se) {
            return Utils.processApiError(se.getErrorMessage(), ErrorCodes.GET);
        } catch (Throwable t) {
            return Utils.processApiError(t.getMessage(), ErrorCodes.GET);
        }
        return houseList;
    }

    @Override
    public Object publishedAdd(User user, String houseId) {

        House house = null;
        try {

            Object object = houseEntityService.findOne(Integer.parseInt(houseId));

            if (object != null) {
                house = (House) object;
            }

        } catch (ServiceException se) {
            return Utils.processApiError(se.getErrorMessage(), ErrorCodes.GET);
        } catch (Throwable t) {
            return Utils.processApiError(t.getMessage(), ErrorCodes.GET);
        }

        com.rest.database.entity.UserHouse u = new com.rest.database.entity.UserHouse();
        u.setUserId(user);
        u.setHouseId(house);
        u.setStatus(Defs.STATUS_INACTIVE);
        Object uh = null;
        try {
            uh = houseEntityService.publishedAdd(u);
        } catch (ServiceException ex) {
            return Utils.processApiError(ex.getErrorMessage(), ErrorCodes.GET);
        }
        if (uh != null) {
            return uh;
        } else {
            return Utils.processApiError("Unexpected error occured.", ErrorCodes.INSERT);
        }
    }

    @Override
    public Object createHouse(HouseBO houseBO, String role) {
        HouseSummary house = new HouseSummary();
        com.rest.database.entity.House houseEO = new com.rest.database.entity.House();
        houseEO.setArea(houseBO.getArea());
        houseEO.setRoadNo(houseBO.getRoadNo());
        houseEO.setHouseNo(houseBO.getHouseNo());
        if (houseBO.getRentType() != null) {
            houseEO.setRentType(houseBO.getRentType().intValue());
        }
        if (!Utils.isEmpty(houseBO.getPersonCount())) {
            houseEO.setPersonCount(Integer.parseInt(houseBO.getPersonCount()));
        }
        if (!Utils.isEmpty(houseBO.getRentCost())) {
            houseEO.setRentCost(Integer.parseInt(houseBO.getRentCost()));
        }
        houseEO.setDescription(houseBO.getDescription());
        if (!Utils.isEmpty(houseBO.getFromDate())) {
            houseEO.setFromDate(Utils.getNextMonthFirstDay());
        } else {
            try {
                houseEO.setFromDate(new SimpleDateFormat(Defs.DB_DATE_FORMAT).parse(houseBO.getFromDate()));
            } catch (ParseException e) {

            }
        }

        if (houseBO.getPublished() != null) {
            houseEO.setPublished(houseBO.getPublished().intValue());
        }
        Date date = new Date();
        houseEO.setCreationDate(date);
        houseEO.setLastUpdatedDate(date);
        houseEO.setCreatedBy(Utils.getCurrentUser()); // if current user not found then default text returned
        houseEO.setLastUpdatedBy(Defs.CREATED_BY_USER);
        //if(Utils.isEmpty(role)){
        role = Defs.ROLE_USER;
        //}
        try {
            Object object = houseEntityService.createHouse(houseEO);
            if (object != null && object instanceof House) {
                House h = (House) object;
                house.setId(h.getId());
                house.setArea(h.getArea());
                house.setRoadNo(h.getRoadNo());
                house.setHouseNo(h.getHouseNo());
                house.setRentType(h.getRentType());
                house.setPersonCount(h.getPersonCount());
                house.setRentCost(h.getRentCost());
                house.setDescription(h.getDescription());
                house.setFromDate(Utils.getDateToString(h.getFromDate()));
                house.setPublished(h.getPublished());
            }
        } catch (ServiceException e) {
            return Utils.processApiError(e.getErrorMessage(), e.getErrorCode());
        }
        return house;
    }

    @Override
    public Object updateHouse(HouseBO houseBO, Integer userId, Integer houseId) {
        HouseSummary hb = new HouseSummary();
        if (userId != null && houseId != null) {
            Object rentalHouseObject = rentalService.getMinimumOneHouseByUserId(userId, houseId);
            //UserHouse uh = (UserHouse)rentalHouseObject;
            if (rentalHouseObject != null && rentalHouseObject instanceof UserHouse) {
                try {
                    com.rest.database.entity.House houseEO = null;
                    houseEO = houseEntityService.getReference(com.rest.database.entity.House.class, houseId);
                    if (!Utils.isEmpty(houseBO.getArea())) {
                        houseEO.setArea(houseBO.getArea());
                    }
                    if (!Utils.isEmpty(houseBO.getRoadNo())) {
                        houseEO.setRoadNo(houseBO.getRoadNo());
                    }
                    if (!Utils.isEmpty(houseBO.getRoadNo())) {
                        houseEO.setRoadNo(houseBO.getRoadNo());
                    }
                    if (!Utils.isEmpty(houseBO.getHouseNo())) {
                        houseEO.setHouseNo(houseBO.getHouseNo());
                    }
                    if (houseBO.getRentType() != null) {
                        houseEO.setRentType(houseBO.getRentType());
                    }
                    if (!Utils.isEmpty(houseBO.getPersonCount())) {
                        houseEO.setPersonCount(Integer.parseInt(houseBO.getPersonCount()));
                    }
                    if (!Utils.isEmpty(houseBO.getRentCost())) {
                        houseEO.setRentCost(Integer.parseInt(houseBO.getRentCost()));
                    }
                    if (!Utils.isEmpty(houseBO.getDescription())) {
                        houseEO.setDescription(houseBO.getDescription());
                    }

                    if (!Utils.isEmpty(houseBO.getFromDate())) {
                        houseEO.setFromDate(Utils.getStringToDate(houseBO.getFromDate()));
                    }

                    if (houseBO.getPublished() != null) {
                        houseEO.setPublished(houseBO.getPublished());
                    }

                    houseEO = houseEntityService.updateHouse(houseEO);
                    hb = hb.getHouseSummaryObject(houseEO);
                    return hb;
                } catch (ServiceException ex) {
                    Logger.getLogger(HouseManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    return Utils.processApiError("Invalid Data type for updating house", ErrorCodes.INVALID);
                }
            }
        }
        return Utils.processApiError("No user / house Found or found invalid data", ErrorCodes.INVALID);
    }

}

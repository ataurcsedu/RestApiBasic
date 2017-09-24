/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.entity.house.HouseBO;
import com.rest.business.entity.house.HouseSummary;
import com.rest.database.entity.User;

/**
 *
 * @author Ataur Rahman
 */
public interface IHouseManager {
    public Object createHouse(HouseBO houseBO,String role);
    public Object updateHouse(HouseBO houseBO,Integer userId, Integer houseId);
    public Object getHouseById(int id);
    public Object getHouseByCriteria(Long startIndex, Long limit,HouseSummary houseBO);
    public Object publishedAdd(User user,String houseId);
}

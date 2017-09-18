/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.house.entity.HouseSummary;

/**
 *
 * @author Ataur Rahman
 */

public interface IRentalHouseManager {
    public Object getRentalInfoByCriteria(Long startIndex, Long limit,HouseSummary houseBO);
    public Object getMinimumOneHouseByUserId(Integer userId, Integer houseId);
}

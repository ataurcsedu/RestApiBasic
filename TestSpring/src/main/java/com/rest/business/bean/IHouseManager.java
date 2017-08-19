/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.house.entity.HouseBO;

/**
 *
 * @author Ataur Rahman
 */
public interface IHouseManager {
    public Object createHouse(HouseBO houseBO,String role);
    public Object getHouseById(int id);
    public Object getHouseByCriteria(Long startIndex, Long limit,HouseBO houseBO);
}

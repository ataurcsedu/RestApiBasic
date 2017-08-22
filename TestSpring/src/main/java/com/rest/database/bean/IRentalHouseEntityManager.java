/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import com.rest.exception.ServiceException;

/**
 *
 * @author Ataur Rahman
 */
public interface IRentalHouseEntityManager {
    public Object getRentalInfoByCriteria(int startIndex, int limit, String where) throws ServiceException;
}

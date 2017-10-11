/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.bean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ataur Rahman
 */
public class BaseEntityManager {

    private static EntityManagerFactory emf;

    public EntityManagerFactory getEntityManagerFactory() throws Throwable {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("EMPU");
        }

        return emf;
    }
}

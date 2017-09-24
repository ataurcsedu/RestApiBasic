/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.entity.house;

import com.rest.utils.Utils;
import java.util.Date;

/**
 *
 * @author Ataur Rahman
 */
public class HouseSummary {
    
    private int id;
    private String area;
    private String roadNo;
    private String houseNo;
    private int rentType;
    private int personCount;
    private int rentCost;
    private String description;
    private String fromDate;
    private int published;
    
    public HouseSummary(){
        
    }

    
    public HouseSummary getHouseSummaryObject(com.rest.database.entity.House h){
        HouseSummary house = new HouseSummary();
        house.setId(h.getId());
        house.setArea(h.getArea());
        house.setRoadNo(h.getRoadNo());
        house.setHouseNo(h.getHouseNo());
        house.setRentType(h.getRentType());
        if(h.getPersonCount()!=null){
            house.setPersonCount(h.getPersonCount());
        }
        house.setRentCost(h.getRentCost());
        
        house.setDescription(h.getDescription());
        house.setFromDate(Utils.getDateToString(h.getFromDate()));
        house.setPublished(h.getPublished());
        return house;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    public int getRentType() {
        return rentType;
    }

    public void setRentType(int rentType) {
        this.rentType = rentType;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getRentCost() {
        return rentCost;
    }

    public void setRentCost(int rentCost) {
        this.rentCost = rentCost;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    
    
}

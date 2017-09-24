/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.entity.house;

import com.rest.utils.Utils;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ataur Rahman
 */
public class HouseBO {
    
    private int id;
    @NotNull
    @Size(min = 1)
    private String area;
    @NotNull
    @Size(min = 1)
    private String roadNo;
    @NotNull
    @Size(min = 1)
    private String houseNo;
    
    private Integer rentType;
    
    @NotNull
    @Size(min = 1)
    private String personCount;
    @NotNull
    @Size(min = 1)
    private String rentCost;
    @NotNull
    @Size(min = 1)
    private String description;
    @NotNull
    @Size(min = 1)
    private String fromDate;
    
    @NotNull
    private Integer published;
    
    private String createdBy;
    private String creationDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    
    
    
    public HouseBO(){
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

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }





    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getRentCost() {
        return rentCost;
    }

    public void setRentCost(String rentCost) {
        this.rentCost = rentCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    
    
    
}

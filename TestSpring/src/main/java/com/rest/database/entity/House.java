/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.database.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rezwan
 */
@Entity
@Table(name = "house")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "House.findAll", query = "SELECT h FROM House h"),
    @NamedQuery(name = "House.findById", query = "SELECT h FROM House h WHERE h.id = :id"),
    @NamedQuery(name = "House.findByArea", query = "SELECT h FROM House h WHERE h.area = :area"),
    @NamedQuery(name = "House.findByRoadNo", query = "SELECT h FROM House h WHERE h.roadNo = :roadNo"),
    @NamedQuery(name = "House.findByHouseNo", query = "SELECT h FROM House h WHERE h.houseNo = :houseNo"),
    @NamedQuery(name = "House.findByRentType", query = "SELECT h FROM House h WHERE h.rentType = :rentType"),
    @NamedQuery(name = "House.findByPersonCount", query = "SELECT h FROM House h WHERE h.personCount = :personCount"),
    @NamedQuery(name = "House.findByRentCost", query = "SELECT h FROM House h WHERE h.rentCost = :rentCost"),
    @NamedQuery(name = "House.findByDescription", query = "SELECT h FROM House h WHERE h.description = :description"),
    @NamedQuery(name = "House.findByFromDate", query = "SELECT h FROM House h WHERE h.fromDate = :fromDate"),
    @NamedQuery(name = "House.findByPublished", query = "SELECT h FROM House h WHERE h.published = :published"),
    @NamedQuery(name = "House.findByCreatedBy", query = "SELECT h FROM House h WHERE h.createdBy = :createdBy"),
    @NamedQuery(name = "House.findByCreationDate", query = "SELECT h FROM House h WHERE h.creationDate = :creationDate"),
    @NamedQuery(name = "House.findByLastUpdatedBy", query = "SELECT h FROM House h WHERE h.lastUpdatedBy = :lastUpdatedBy"),
    @NamedQuery(name = "House.findByLastUpdatedDate", query = "SELECT h FROM House h WHERE h.lastUpdatedDate = :lastUpdatedDate")})
public class House implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "area")
    private String area;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "road_no")
    private String roadNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "house_no")
    private String houseNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rent_type")
    private int rentType;
    @Column(name = "person_count")
    private Integer personCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rent_cost")
    private int rentCost;
    @Size(max = 50)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromDate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "published")
    private int published;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_updated_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdatedDate;

    public House() {
    }

    public House(Integer id) {
        this.id = id;
    }

    public House(Integer id, String area, String roadNo, String houseNo, int rentType, int rentCost, Date fromDate, int published, String createdBy, Date creationDate, String lastUpdatedBy, Date lastUpdatedDate) {
        this.id = id;
        this.area = area;
        this.roadNo = roadNo;
        this.houseNo = houseNo;
        this.rentType = rentType;
        this.rentCost = rentCost;
        this.fromDate = fromDate;
        this.published = published;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getRentType() {
        return rentType;
    }

    public void setRentType(int rentType) {
        this.rentType = rentType;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public int getRentCost() {
        return rentCost;
    }

    public void setRentCost(int rentCost) {
        this.rentCost = rentCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof House)) {
            return false;
        }
        House other = (House) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rest.database.entity.House[ id=" + id + " ]";
    }
    
}

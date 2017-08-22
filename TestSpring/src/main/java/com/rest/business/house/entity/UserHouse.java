/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.house.entity;

import com.rest.database.entity.House;
import com.rest.database.entity.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "user_house")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserHouse.findAll", query = "SELECT u FROM UserHouse u"),
    @NamedQuery(name = "UserHouse.findById", query = "SELECT u FROM UserHouse u WHERE u.id = :id"),
    @NamedQuery(name = "UserHouse.findByPriority", query = "SELECT u FROM UserHouse u WHERE u.priority = :priority"),
    @NamedQuery(name = "UserHouse.findByStatus", query = "SELECT u FROM UserHouse u WHERE u.status = :status"),
    @NamedQuery(name = "UserHouse.findByApproveDate", query = "SELECT u FROM UserHouse u WHERE u.approveDate = :approveDate"),
    @NamedQuery(name = "UserHouse.findByApprovedBy", query = "SELECT u FROM UserHouse u WHERE u.approvedBy = :approvedBy")})
public class UserHouse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priority")
    private int priority;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "status")
    private String status;
    @Column(name = "approve_date")
    @Temporal(TemporalType.DATE)
    private Date approveDate;
    @Column(name = "approved_by")
    private Integer approvedBy;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private House houseId;

    public UserHouse() {
    }

    public UserHouse(Integer id) {
        this.id = id;
    }

    public UserHouse(Integer id, int priority, String status) {
        this.id = id;
        this.priority = priority;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public House getHouseId() {
        return houseId;
    }

    public void setHouseId(House houseId) {
        this.houseId = houseId;
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
        if (!(object instanceof UserHouse)) {
            return false;
        }
        UserHouse other = (UserHouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rest.business.house.entity.UserHouse[ id=" + id + " ]";
    }
    
}

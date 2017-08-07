/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.ws.response;

import com.rest.business.user.entity.UserSummary;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ataur Rahman
 */
public class GetUserServiceResponse extends BaseResponse implements Serializable{
    List<UserSummary> userList = new ArrayList<UserSummary>();
    Long totalCount;
    
    public GetUserServiceResponse(){
        super();
    }

    public List<UserSummary> getUserList() {
        return userList;
    }

    public void setUserList(List<UserSummary> userList) {
        this.userList = userList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
    
    
    
}

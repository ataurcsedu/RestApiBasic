/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.user.entity.UserBO;
import com.rest.business.user.entity.UserSummary;
import com.rest.database.bean.IUserEntityManager;
import com.rest.database.bean.UserEntityManagerBean;
import com.rest.exception.ServiceException;
import com.rest.utils.Utils;
import com.rest.ws.response.GetUserServiceResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */
@Service
public class UserManagerBean implements IUserManager {
    
    @Autowired
    IUserEntityManager userEntityService;
    
    @Override
    public GetUserServiceResponse getUsers(Long startIndex,Long limit,UserBO userBO){
        GetUserServiceResponse response = new GetUserServiceResponse();
        List<UserSummary> userList = new ArrayList<UserSummary>();
        try{
        if (startIndex == null || Utils.compareLong(startIndex, 0L)) {
                startIndex = 0L;
        }
        if (limit == null || Utils.compareLong(limit, 0L)) {
            limit = 100L;
        }
        
        
        //UserEntityManagerBean umb = new UserEntityManagerBean();
        String where = "";

        where += Utils.buildJPQLLikeQuery("u.userName", userBO.getUserName());
        where += Utils.buildJPQLLikeQuery("u.status", userBO.getStatus());
        
        Object object = userEntityService.getUsers(startIndex.intValue(), limit.intValue(), where);
        
        if (object != null) {
                List<Object> list = (List<Object>) object;
                for (Object obj : list) {
                    Object[] objArr = (Object[]) obj;
                    UserSummary user = new UserSummary();
                    
                    user.setId((Integer)objArr[0]);
                    user.setUserName((String) objArr[1]);
                    user.setEmail((String) objArr[2]);
                    user.setStatus((String) objArr[3]);
                    
                    userList.add(user);
                }
            }
        
        
        response.setUserList(userList);
        Long totalCount = userEntityService.getTotalCount("");
        response.setTotalCount(totalCount);
        response.getOperationResult().setSuccess(true);
        }catch(ServiceException se){
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(se.getErrorMessage()));
        }catch(Throwable t){
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(t.getMessage()));
        }
        return response;
    }
    
    @Override
    public UserSummary getUser(String userId){
        GetUserServiceResponse response = new GetUserServiceResponse();
        UserSummary user = new UserSummary();
        try{
        
        
        
        //UserEntityManagerBean umb = new UserEntityManagerBean();
        String where = "";

        where += Utils.buildJPQLLikeQuery("u.id", userId);
        where += Utils.buildJPQLLikeQuery("u.status", "ACTIVE");
        
        Object object = userEntityService.getUser(where);
        
        if (object != null) {
                List<Object> list = (List<Object>) object;
                for (Object obj : list) {
                    Object[] objArr = (Object[]) obj;
                    UserSummary userSummary = new UserSummary();
                    
                    userSummary.setId((Integer)objArr[0]);
                    userSummary.setUserName((String) objArr[1]);
                    userSummary.setEmail((String) objArr[2]);
                    userSummary.setStatus((String) objArr[3]);
                    user=userSummary;
                    
                }
            }
        
            response.getOperationResult().setSuccess(true);
        }catch(ServiceException se){
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(se.getErrorMessage()));
        }catch(Throwable t){
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(t.getMessage()));
        }
        return user;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.business.bean;

import com.rest.business.entity.user.UserBO;
import com.rest.business.entity.user.UserSummary;
import com.rest.database.bean.IUserEntityManager;
import com.rest.database.bean.UserEntityManagerBean;
import com.rest.database.entity.User;
import com.rest.exception.NonExistentEntityException;
import com.rest.exception.ServiceException;
import com.rest.utils.Defs;
import com.rest.utils.ErrorCodes;
import com.rest.utils.Utils;
import com.rest.ws.response.GetUserServiceResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ataur Rahman
 */
@Service
public class UserManagerBean implements IUserManager {

    @Autowired
    private IUserEntityManager userEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Object getUsers(Long startIndex, Long limit, UserBO userBO) {
        GetUserServiceResponse response = new GetUserServiceResponse();
        List<UserSummary> userList = new ArrayList<UserSummary>();
        try {
            if (startIndex == null || Utils.compareLong(startIndex, 0L)) {
                startIndex = 0L;
            }
            if (limit == null || Utils.compareLong(limit, 0L)) {
                limit = 100L;
            }

            //UserEntityManagerBean umb = new UserEntityManagerBean();
            String where = "";

            where += Utils.buildJPQLLikeQuery("u.username", userBO.getUserName());
            where += Utils.buildJPQLLikeQuery("u.status", userBO.getStatus());

            Object object = userEntityService.getUsers(startIndex.intValue(), limit.intValue(), where);

            if (object != null) {
                List<Object> list = (List<Object>) object;
                for (Object obj : list) {
                    Object[] objArr = (Object[]) obj;
                    UserSummary user = new UserSummary();

                    user.setId((Integer) objArr[0]);
                    user.setMobile((String) objArr[1]);
                    user.setEmail((String) objArr[2]);
                    user.setUserName((String) objArr[3]);
                    user.setFullName((String) objArr[4]);
                    user.setDob(Utils.getDateToString((Date) objArr[5]));
                    user.setCode((String) objArr[6]);
                    user.setSex((String) objArr[7]);
                    user.setStatus((String) objArr[8]);

                    userList.add(user);
                }
            }

            response.setUserList(userList);
            Long totalCount = userEntityService.getTotalCount("");
            response.setTotalCount(totalCount);
            response.getOperationResult().setSuccess(true);
        } catch (ServiceException se) {
            return Utils.processApiError(se.getErrorMessage(), ErrorCodes.GET);
            
        } catch (Throwable t) {
            return Utils.processApiError(t.getMessage(), ErrorCodes.GET);
        }
        return userList;
    }

    @Override
    public UserSummary getUser(String userId) {
        GetUserServiceResponse response = new GetUserServiceResponse();
        UserSummary user = new UserSummary();
        try {

            //UserEntityManagerBean umb = new UserEntityManagerBean();
            String where = "";

            where += Utils.buildEqualQuery("u.id", userId);
            where += Utils.buildEqualQuery("u.status", "ACTIVE");

            Object object = userEntityService.getUser(where);

            if (object != null) {
                User u = (User) object;
                UserSummary userSummary = new UserSummary();

                userSummary.setId(u.getId());
                userSummary.setMobile(u.getMobile());
                userSummary.setEmail(u.getEmail());
                userSummary.setUserName(u.getUsername());
                userSummary.setFullName(u.getFullname());
                userSummary.setDob(Utils.getDateToString(u.getDob()));
                userSummary.setCode(u.getCode());
                userSummary.setSex(u.getSex());
                userSummary.setStatus(u.getStatus());
                user = userSummary;
            }

            response.getOperationResult().setSuccess(true);
        } catch (ServiceException se) {
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(se.getErrorMessage()));
        } catch (Throwable t) {
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(t.getMessage()));
        }
        return user;
    }

    @Override
    public User getUser(String userName, String password) {
        GetUserServiceResponse response = new GetUserServiceResponse();
        User userEO = null;
        try {

            //UserEntityManagerBean umb = new UserEntityManagerBean();
            String where = "";

            where += Utils.buildEqualQuery("u.username", userName);
            where += Utils.buildEqualQuery("u.password", password);
            where += Utils.buildEqualQuery("u.status", Defs.STATUS_ACTIVE);

            userEO = userEntityService.getUser(where);

            response.getOperationResult().setSuccess(true);
        } catch (ServiceException se) {
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(se.getErrorMessage()));
        } catch (Throwable t) {
            response.getOperationResult().setSuccess(false);
            response.getOperationResult().setErrorList(Arrays.asList(t.getMessage()));
        }
        return userEO;
    }
    
    @Override
    public List<String> getUserByName(String userName) {
        GetUserServiceResponse response = new GetUserServiceResponse();
        List <String> userNameList = new ArrayList<String>();
        Object object = null;
        try {

            //UserEntityManagerBean umb = new UserEntityManagerBean();
            String where = "";

            where += Utils.buildJPQLLikeQuery("u.username", userName);
            where += Utils.buildEqualQuery("u.status", Defs.STATUS_ACTIVE);
            
            object = userEntityService.getOnlyUserNameByUserName(where);
            if(object!=null && object instanceof  List){
                List<Object> sList = (List<Object>) object;
                for (Object s : sList) {
                    String un = (String)s;
                    userNameList.add(un);
                }
            }
            
        }catch (Throwable t) {
            throw new UsernameNotFoundException("User name not found");
        }
        return userNameList;
    }

    @Override
    public Object createUser(UserBO user, String role) {
        UserSummary userSummary = new UserSummary();
        Date dob = Utils.getStringToDate(user.getDob());
        if(dob==null || dob.toString().length() == 0){
            return Utils.processApiError("Date of birth can not be empty.", ErrorCodes.INVALID);
        }
        com.rest.database.entity.User userEO = new com.rest.database.entity.User();
        String password = passwordEncoder.encode(user.getPassword());
        userEO.setUsername(user.getUserName());
        userEO.setPassword(password);
        userEO.setFullname(user.getFullName());
        userEO.setStatus(Defs.STATUS_INACTIVE);
        userEO.setEnabled(0);
        userEO.setEmail(user.getEmail());
        userEO.setCode(Utils.generateSixDigitUniqueNumber());
        userEO.setMobile(user.getMobile());
        if (user.getSex() != null) {
            userEO.setSex(user.getSex());
        }
        
        
        userEO.setDob(dob);
        Date date = new Date();
        userEO.setCreationDate(date);
        userEO.setLastUpdateDate(date);
        userEO.setCreatedBy(Defs.CREATED_BY_USER);
        userEO.setLastUpdatedBy(Defs.CREATED_BY_USER);
        //if(Utils.isEmpty(role)){
        role = Defs.ROLE_USER;
        //}
        try {
            Object object = userEntityService.createUser(userEO, role);
            if (object != null && object instanceof User) {
                User u = (User) object;
                userSummary.setId(u.getId());
                userSummary.setMobile(u.getMobile());
                userSummary.setEmail(u.getEmail());
                userSummary.setUserName(u.getUsername());
                userSummary.setFullName(u.getFullname());
                userSummary.setDob(Utils.getDateToString(u.getDob()));
                userSummary.setSex(u.getSex());
                userSummary.setCode(u.getCode());
                userSummary.setStatus(u.getStatus());
            }else if(object != null && object instanceof ServiceException){
                ServiceException s = (ServiceException)object;
                return Utils.processApiError(s.getErrorMessage(), s.getErrorCode());
            }
        } catch (ServiceException e) {
            return Utils.processApiError(e.getErrorMessage(), e.getErrorCode());
        }
        return userSummary;
    }

    @Override
    public Object updateUser(UserBO user, int id) {
        UserSummary userSummary = new UserSummary();
        
        
        com.rest.database.entity.User userEO = new com.rest.database.entity.User();
        Object uobj = null;
        try {
            userEO = userEntityService.getReference(com.rest.database.entity.User.class,id);
        } catch (Exception e) {
            return Utils.processApiError(e.getMessage(), ErrorCodes.GET);
        }

        if (userEO != null) {
            
            String password = null;
            if(!Utils.isEmpty(user.getPassword())){
                password = passwordEncoder.encode(user.getPassword());
            }
            if(!Utils.isEmpty(user.getUserName())){
                userEO.setUsername(user.getUserName());
            }
            if(!Utils.isEmpty(password)){
                userEO.setPassword(password);
            }
            if(!Utils.isEmpty(user.getFullName())){
                userEO.setFullname(user.getFullName());
            }
            if(!Utils.isEmpty(user.getEmail())){
                userEO.setEmail(user.getEmail());
            }
            userEO.setCode(Utils.generateSixDigitUniqueNumber());
            if(!Utils.isEmpty(user.getMobile())){
                userEO.setMobile(user.getMobile());
            }
            Date dob = Utils.getStringToDate(user.getDob());
            if(!Utils.isEmpty(user.getDob())){
                userEO.setDob(dob);
            }
            Date date = new Date();
            userEO.setCreationDate(date);
            userEO.setLastUpdateDate(date);
            userEO.setCreatedBy(Defs.CREATED_BY_USER);
            userEO.setLastUpdatedBy(Defs.CREATED_BY_USER);

            try {
                Object object = userEntityService.updateUser(userEO);
                if (object != null && object instanceof User) {
                    User u = (User) object;
                    userSummary.setId(u.getId());
                    userSummary.setMobile(u.getMobile());
                    userSummary.setEmail(u.getEmail());
                    userSummary.setUserName(u.getUsername());
                    userSummary.setFullName(u.getFullname());
                    userSummary.setDob(Utils.getDateToString(u.getDob()));
                    userSummary.setCode(u.getCode());
                    userSummary.setSex(u.getSex());
                    userSummary.setStatus(u.getStatus());
                }
            } catch (ServiceException e) {
                return Utils.processApiError(e.getErrorMessage(), e.getErrorCode());
            }
        }
        if (uobj == null) {
            return Utils.processApiError("No Entity exist with the id : " + id, ErrorCodes.GET);
        }
        return userSummary;
    }

    @Override
    public UserSummary activateUser(int id) throws ServiceException {
        UserSummary userSummary = new UserSummary();
        com.rest.database.entity.User userEO = null;
        Object uobj = null;
        try {
            uobj = userEntityService.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), ErrorCodes.GET);
        }

        if (uobj != null || uobj instanceof User) {
            userEO = (User) uobj;

            userEO.setStatus(Defs.STATUS_ACTIVE);
            userEO.setEnabled(1);
            Date date = new Date();
            //userEO.setCreationDate(date);
            userEO.setLastUpdateDate(date);
            //userEO.setCreatedBy(Defs.CREATED_BY_USER);
            userEO.setLastUpdatedBy(Defs.CREATED_BY_USER);

            try {
                Object object = userEntityService.updateUser(userEO);
                if (object != null && object instanceof User) {
                    User u = (User) object;
                    userSummary.setId(u.getId());
                    userSummary.setMobile(u.getMobile());
                    userSummary.setEmail(u.getEmail());
                    userSummary.setUserName(u.getUsername());
                    userSummary.setFullName(u.getFullname());
                    userSummary.setDob(Utils.getDateToString(u.getDob()));
                    userSummary.setCode(u.getCode());
                    userSummary.setStatus(u.getStatus());
                }
            } catch (ServiceException e) {
                throw new ServiceException(e.getErrorMessage(), e.getErrorCode());
            }
        }
        if (uobj == null) {
            throw new ServiceException("No Entity exist with the id : " + id, ErrorCodes.GET);
        }
        return userSummary;
    }

}

package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.UserDao;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.User;
import com.classbooking.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public LYPResult login(String email, String password) {
        User user;
        user = userDao.selectByLoginnameAndPassword(email, password);
        if(user == null){
            return new LYPResult().setSuccess(false);
        }
        return new LYPResult().setData(user);
    }

    @Override
    public LYPResult signUp(String email, String passowrd) {
        return null;
    }

}

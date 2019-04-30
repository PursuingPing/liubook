package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.UserDao;
import com.classbooking.web.domain.Dto;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.User;
import com.classbooking.web.service.UserService;
import com.classbooking.web.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User login(String email, String password , HttpServletRequest request) {
        User user;
        user = userDao.login(email, password);
        WebUtils.setSessionAttribute(request,"user",user);
        return user;
    }

    @Override
    public int register(User user) {
        int ack = userDao.register(user);
        new Thread(new MailUtil(user.getEmail(),user.getCode())).start();
        return ack;
    }

    @Override
    public User findByCode(String code) {
        return userDao.findByCode(code);
    }

    @Override
    public int update(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public String findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return userDao.updatePassword(email, password) == 1;
    }

    @Override
    public boolean isPasswordCorrect(String email, String password) {
        User user;
        user = userDao.login(email, password);
        return user!=null;
    }

    @Override
    public List<String> getAllSEmail() {
        return userDao.getAllSEmail();
    }

    @Override
    public List<String> getAllTEmail() {
        return userDao.getAllTEmail();
    }

    @Override
    public List<String> getAllMEmail() {
        return userDao.getAllMEmail();
    }

}

package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.UserMapper;
import com.ljh.jhoj.model.UserBean;
import com.ljh.jhoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int addNewUser(UserBean user) {
        return userMapper.addNewUser(user);
    }

    @Override
    public void deleteUserById(Integer userID) {
        userMapper.deleteUserById(userID);
    }

    @Override
    public void updateUser(UserBean user) {
        userMapper.updateUser(user);
    }

    @Override
    public UserBean getUserByID(int userID) {
        return userMapper.getUserByID(userID);
    }

    @Override
    public UserBean getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public List<String> getUserEmailList() {
        return userMapper.getUserEmailList();
    }

    @Override
    public List<UserBean> getUserList(String keyword, Integer start, Integer count) {
        return userMapper.getUserList(keyword, start, count);
    }

    @Override
    public Integer getCountOfSearch(String keyword) {
        return userMapper.getCountOfSearch(keyword);
    }

    @Override
    public List<UserBean> getChart(Integer start, Integer count) {
        return userMapper.getChart(start, count);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userMapper.checkEmailExist(email);
    }

    @Override
    public boolean checkUserNameExist(String userName) {
        return userMapper.checkUserNameExist(userName);
    }

    @Override
    public int getCount() {
        return userMapper.getCount();
    }
}

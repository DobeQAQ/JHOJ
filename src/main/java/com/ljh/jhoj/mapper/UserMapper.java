package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    //insert
    int addNewUser(@Param("user") UserBean user);

    //delete
    void deleteUserById(@Param("userID") Integer userID);


    //update
    void updateUser(@Param("user") UserBean user);

    //select one
    UserBean getUserByID(@Param("userID") int userID);

    UserBean getUserByEmail(@Param("email") String email);


    //select list
    List<String> getUserEmailList();

    List<UserBean> getUserList(@Param("keyword") String keyword, @Param("start") Integer start, @Param("count") Integer count);

    Integer getCountOfSearch(@Param("keyword") String keyword);

    List<UserBean> getChart(@Param("start") Integer start, @Param("count") Integer count);

    //check
    boolean checkEmailExist(@Param("email") String email);

    boolean checkUserNameExist(@Param("userName") String userName);

    int getCount();
}

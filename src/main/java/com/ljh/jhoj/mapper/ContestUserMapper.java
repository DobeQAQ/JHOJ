package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.ContestUserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestUserMapper {
    int addUser(@Param("contestUser") ContestUserBean contestUser);

    void deleteUser(@Param("contestID") Integer contestID, @Param("userID") Integer userID);

    boolean checkUserRegistered(@Param("contestID") int contestID, @Param("userID") Integer userID);

    List<ContestUserBean> getContestUserList(@Param("contestID") Integer contestID);

    Integer getContestUserCount(@Param("contestID") Integer contestID);

    int getCount();
}

package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ContestUserMapper;
import com.ljh.jhoj.model.ContestUserBean;
import com.ljh.jhoj.service.ContestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestUserServiceImpl implements ContestUserService {

    @Autowired
    private ContestUserMapper contestUserMapper;

    @Override
    public int addUser(ContestUserBean contestUser) {
        return contestUserMapper.addUser(contestUser);
    }

    @Override
    public void deleteUser(Integer contestID, Integer userID) {
        contestUserMapper.deleteUser(contestID,userID);
    }

    @Override
    public boolean checkUserRegistered(int contestID, Integer userID) {
        return contestUserMapper.checkUserRegistered(contestID,userID);
    }

    @Override
    public List<ContestUserBean> getContestUserList(Integer contestID) {
        return contestUserMapper.getContestUserList(contestID);
    }

    @Override
    public Integer getContestUserCount(Integer contestID) {
        return contestUserMapper.getContestUserCount(contestID);
    }

    @Override
    public int getCount() {
        return contestUserMapper.getCount();
    }
}

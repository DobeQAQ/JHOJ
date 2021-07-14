package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ContestMapper;
import com.ljh.jhoj.model.ContestBean;
import com.ljh.jhoj.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public int addContest(ContestBean contest) {
        return contestMapper.addContest(contest);
    }

    @Override
    public void deleteContest(Integer contestID) {
        contestMapper.deleteContest(contestID);
    }

    @Override
    public void updateContest(ContestBean contest) {
        contestMapper.updateContest(contest);
    }

    @Override
    public ContestBean getContestByID(Integer contestID) {
        return contestMapper.getContestByID(contestID);
    }

    @Override
    public void getContestByTitle(String title) {
        contestMapper.getContestByTitle(title);
    }

    @Override
    public List<ContestBean> getContestList(Integer start, Integer count) {
        return contestMapper.getContestList(start,count);
    }

    @Override
    public int getCount() {
        return contestMapper.getCount();
    }
}

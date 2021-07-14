package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ContestProblemMapper;
import com.ljh.jhoj.model.ContestProblemBean;
import com.ljh.jhoj.service.ContestProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestProblemServiceImpl implements ContestProblemService {

    @Autowired
    private ContestProblemMapper contestProblemMapper;

    @Override
    public int addProblem(ContestProblemBean contestProblem) {
        return contestProblemMapper.addProblem(contestProblem);
    }

    @Override
    public void deleteProblem(Integer contestID, String innerID) {
        contestProblemMapper.deleteProblem(contestID,innerID);
    }

    @Override
    public void deleteAllProblem(Integer contestID) {
        contestProblemMapper.deleteAllProblem(contestID);
    }

    @Override
    public void updateProblem(ContestProblemBean contestProblem) {
        contestProblemMapper.updateProblem(contestProblem);
    }

    @Override
    public ContestProblemBean getContestProblem(Integer contestID, int problemID) {
        return contestProblemMapper.getContestProblem(contestID,problemID);
    }

    @Override
    public List<ContestProblemBean> getContestProblemList(Integer contestID) {
        return contestProblemMapper.getContestProblemList(contestID);
    }
}

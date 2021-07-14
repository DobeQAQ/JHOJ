package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ProblemMapper;
import com.ljh.jhoj.model.ProblemBean;
import com.ljh.jhoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public int addProblem(ProblemBean problem) {
        return problemMapper.addProblem(problem);
    }

    @Override
    public void deleteProblemByID(int problemID) {
        problemMapper.deleteProblemByID(problemID);
    }

    @Override
    public void updateProblemByID(ProblemBean problem) {
        problemMapper.updateProblemByID(problem);
    }

    @Override
    public void updateSubmittedTimes(Integer problemID) {
        problemMapper.updateSubmittedTimes(problemID);
    }

    @Override
    public void updateAcceptedTimes(Integer problemID) {
        problemMapper.updateAcceptedTimes(problemID);
    }

    @Override
    public ProblemBean getProblemByID(Integer problemID) {
        return problemMapper.getProblemByID(problemID);
    }

    @Override
    public List<ProblemBean> getProblemsOrderByID(Integer start, Integer count) {
        return problemMapper.getProblemsOrderByID(start, count);
    }

    @Override
    public List<ProblemBean> getProblesOrderByIDForLogin(Integer userID, String result, Integer start, Integer count) {
        return problemMapper.getProblesOrderByIDForLogin(userID, result, start, count);
    }

    @Override
    public List<ProblemBean> getProblemsOrderByAccepted(Integer start, Integer count) {
        return problemMapper.getProblemsOrderByAccepted(start, count);
    }

    @Override
    public List<ProblemBean> getProblemsOrderBySubmitted(Integer start, Integer count) {
        return problemMapper.getProblemsOrderBySubmitted(start, count);
    }

    @Override
    public List<ProblemBean> getProblemsOrderByAcceptedRate(Integer start, Integer count) {
        return problemMapper.getProblemsOrderByAcceptedRate(start, count);
    }

    @Override
    public List<ProblemBean> searchProblem(Integer problemID, String problemKeyword, Integer start, Integer count) {
        return problemMapper.searchProblem(problemID, problemKeyword, start, count);
    }

    @Override
    public int getSearchResultCount(Integer problemID, String problemKeyword) {
        return problemMapper.getSearchResultCount(problemID, problemKeyword);
    }

    @Override
    public int getCount() {
        return problemMapper.getCount();
    }
}

package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ViewSubmitRecordMapper;
import com.ljh.jhoj.model.SubmitRecordBean;
import com.ljh.jhoj.model.ViewSubmitRecordBean;
import com.ljh.jhoj.service.ViewSubmitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewSubmitRecordServiceImpl implements ViewSubmitRecordService {

    @Autowired
    private ViewSubmitRecordMapper viewSubmitRecordMapper;


    @Override
    public SubmitRecordBean getSubmitRecordByID(Integer submitID) {
        return viewSubmitRecordMapper.getSubmitRecordByID(submitID);
    }

    @Override
    public List<ViewSubmitRecordBean> getSubmitRecordListByProblemID(Integer contestID, Integer problemID) {
        return viewSubmitRecordMapper.getSubmitRecordListByProblemID(contestID, problemID);
    }

    @Override
    public List<ViewSubmitRecordBean> getSubmitRecordListOrderedByDate(Integer contestID, Integer start, Integer count) {
        return viewSubmitRecordMapper.getSubmitRecordListOrderedByDate(contestID, start, count);
    }

    @Override
    public List<Integer> getUserAcceptedProblems(Integer userID) {
        return viewSubmitRecordMapper.getUserAcceptedProblems(userID);
    }

    @Override
    public int getCountOnCondition(Integer contestID, Integer problemID, String userName, String result, String language) {
        return viewSubmitRecordMapper.getCountOnCondition(contestID, problemID, userName, result, language);
    }

    @Override
    public List<ViewSubmitRecordBean> getSubmitRecordList(Integer contestID, Integer problemID, String userName, String result, String language, Integer start, Integer count) {
        return viewSubmitRecordMapper.getSubmitRecordList(contestID, problemID, userName, result, language, start, count);
    }

    @Override
    public int getCount() {
        return viewSubmitRecordMapper.getCount();
    }
}

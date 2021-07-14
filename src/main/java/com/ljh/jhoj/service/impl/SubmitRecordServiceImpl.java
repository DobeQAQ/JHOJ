package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.SubmitRecordMapper;
import com.ljh.jhoj.model.SubmitRecordBean;
import com.ljh.jhoj.service.SubmitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitRecordServiceImpl implements SubmitRecordService {

    @Autowired
    private SubmitRecordMapper submitRecordMapper;

    @Override
    public int addSubmitRecord(SubmitRecordBean submitRecord) {
        return submitRecordMapper.addSubmitRecord(submitRecord);
    }

    @Override
    public void deleteSubmitRecord(int submitID) {
        submitRecordMapper.deleteSubmitRecord(submitID);
    }

    @Override
    public void deleteSubmitRecordByUserID(int userID) {
        submitRecordMapper.deleteSubmitRecordByUserID(userID);
    }

    @Override
    public int updateSubmitRecord(SubmitRecordBean submitRecord) {
        return submitRecordMapper.updateSubmitRecord(submitRecord);
    }

    @Override
    public SubmitRecordBean getSubmitRecordByID(int submitID) {
        return submitRecordMapper.getSubmitRecordByID(submitID);
    }

    @Override
    public List<SubmitRecordBean> getSubmitRecordListByProblemID(int problemID) {
        return submitRecordMapper.getSubmitRecordListByProblemID(problemID);
    }

    @Override
    public List<SubmitRecordBean> getSubmitRecordListByUserID(int userID, int start, int count) {
        return submitRecordMapper.getSubmitRecordListByUserID(userID, start, count);
    }

    @Override
    public List<SubmitRecordBean> getSubmitRecordListByProblemUser(int problemID, int userID, int start, int count) {
        return submitRecordMapper.getSubmitRecordListByProblemUser(problemID, userID, start, count);
    }

    @Override
    public List<SubmitRecordBean> getSubmitRecordListOrderedByDate(int start, int count) {
        return submitRecordMapper.getSubmitRecordListOrderedByDate(start, count);
    }

    @Override
    public List<SubmitRecordBean> getSubmitRecordList(Integer contestID, Integer problemID, Integer userID, String result, String language, int start, int count) {
        return submitRecordMapper.getSubmitRecordList(contestID, problemID, userID, result, language, start, count);
    }
}

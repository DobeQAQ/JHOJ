package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.JudgeDetailMapper;
import com.ljh.jhoj.model.JudgeDetailBean;
import com.ljh.jhoj.service.JudgeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeDetailServiceImpl implements JudgeDetailService {

    @Autowired
    private JudgeDetailMapper judgeDetailMapper;

    @Override
    public int insertJudgeDetail(JudgeDetailBean judgeDetail) {
        return judgeDetailMapper.insertJudgeDetail(judgeDetail);
    }

    @Override
    public void deleteJudgeDetail(int submitID) {
        judgeDetailMapper.deleteJudgeDetail(submitID);
    }

    @Override
    public void updateJudegeDetail(JudgeDetailBean newJudgeDetail) {
        judgeDetailMapper.updateJudegeDetail(newJudgeDetail);
    }

    @Override
    public List<JudgeDetailBean> getJudegeDetailBySubmitID(int submitID) {
        return judgeDetailMapper.getJudegeDetailBySubmitID(submitID);
    }

    @Override
    public JudgeDetailBean getJudgeDetailByTestPointID(int submitID, int testPointID) {
        return judgeDetailMapper.getJudgeDetailByTestPointID(submitID, testPointID);
    }
}

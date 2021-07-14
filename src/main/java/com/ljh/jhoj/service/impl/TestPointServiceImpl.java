package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.TestPointMapper;
import com.ljh.jhoj.model.TestPointBean;
import com.ljh.jhoj.service.TestPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPointServiceImpl implements TestPointService {

    @Autowired
    private TestPointMapper testPointMapper;


    @Override
    public int addTestPoint(TestPointBean testPoint) {
        return testPointMapper.addTestPoint(testPoint);
    }

    @Override
    public void deleteTestPoint(int problemID, int testPointID) {
        testPointMapper.deleteTestPoint(problemID, testPointID);
    }

    @Override
    public void updateTestPoint(TestPointBean testPoint) {
        testPointMapper.updateTestPoint(testPoint);
    }

    @Override
    public List<TestPointBean> getTestPointList(int problemID) {
        return testPointMapper.getTestPointList(problemID);
    }

    @Override
    public TestPointBean getTestPoint(int problemID, int testPointID) {
        return testPointMapper.getTestPoint(problemID, testPointID);
    }
}

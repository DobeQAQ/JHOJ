package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.CompileInfoMapper;
import com.ljh.jhoj.model.CompileInfoBean;
import com.ljh.jhoj.service.CompileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompileInfoServiceImpl implements CompileInfoService {

    @Autowired
    public CompileInfoMapper compileInfoMapper;

    @Override
    public int insertCompileResult(CompileInfoBean compileResult) {
        return compileInfoMapper.insertCompileResult(compileResult);
    }

    @Override
    public void deleteCompileResult(int submitID) {
        compileInfoMapper.deleteCompileResult(submitID);
    }

    @Override
    public CompileInfoBean getCompileResult(int submitID) {
        return compileInfoMapper.getCompileResult(submitID);
    }
}

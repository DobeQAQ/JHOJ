package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.SystemErrorMapper;
import com.ljh.jhoj.model.SystemErrorBean;
import com.ljh.jhoj.service.SystemErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemErrorServiceImpl implements SystemErrorService {

    @Autowired
    private SystemErrorMapper systemErrorMapper;


    @Override
    public void addErrorMessage(SystemErrorBean systemError) {
        systemErrorMapper.addErrorMessage(systemError);
    }

    @Override
    public void deleteErrorMessage(Integer submitID) {
        systemErrorMapper.deleteErrorMessage(submitID);
    }

    @Override
    public SystemErrorBean getSystemErrorMessage(Integer submitID) {
        return systemErrorMapper.getSystemErrorMessage(submitID);
    }
}

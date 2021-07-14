package com.ljh.jhoj.service;

import com.ljh.jhoj.model.SystemErrorBean;
import org.springframework.data.repository.query.Param;

public interface SystemErrorService {
    void addErrorMessage(@Param("systemError") SystemErrorBean systemError);

    void deleteErrorMessage(@Param("submitID") Integer submitID);

    SystemErrorBean getSystemErrorMessage(@Param("submitID") Integer submitID);
}

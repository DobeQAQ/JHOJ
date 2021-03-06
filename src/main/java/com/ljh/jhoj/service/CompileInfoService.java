package com.ljh.jhoj.service;

import com.ljh.jhoj.model.CompileInfoBean;
import org.springframework.data.repository.query.Param;

/**
 * Created by ljh on 18-1-1.
 */
public interface CompileInfoService {
    //insert
    int insertCompileResult(@Param("compileResult") CompileInfoBean compileResult);

    //delete
    void deleteCompileResult(@Param("submitID") int submitID);

    //update  needn't update function

    //select
    CompileInfoBean getCompileResult(@Param("submitID") int submitID);

}

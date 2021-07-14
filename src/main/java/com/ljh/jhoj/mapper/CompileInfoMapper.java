package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.CompileInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompileInfoMapper {

    //insert
    int insertCompileResult(@Param("compileResult") CompileInfoBean compileResult);

    //delete
    void deleteCompileResult(@Param("submitID") int submitID);

    //update  needn't update function

    //select
    CompileInfoBean getCompileResult(@Param("submitID") int submitID);

}

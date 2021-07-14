package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.SystemErrorBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemErrorMapper {
    void addErrorMessage(@Param("systemError") SystemErrorBean systemError);

    void deleteErrorMessage(@Param("submitID") Integer submitID);

    SystemErrorBean getSystemErrorMessage(@Param("submitID") Integer submitID);
}

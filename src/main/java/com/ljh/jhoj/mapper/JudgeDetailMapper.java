package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.JudgeDetailBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeDetailMapper {
    //insert
    int insertJudgeDetail(@Param("judgeDetail") JudgeDetailBean judgeDetail);

    //delete
    void deleteJudgeDetail(@Param("submitID") int submitID);

    //update rejudge may user this function
    void updateJudegeDetail(@Param("newJudgeDetail") JudgeDetailBean newJudgeDetail);

    //select
    List<JudgeDetailBean> getJudegeDetailBySubmitID(@Param("submitID") int submitID);

    JudgeDetailBean getJudgeDetailByTestPointID(@Param("submitID") int submitID, @Param("testPointID") int testPointID);
}

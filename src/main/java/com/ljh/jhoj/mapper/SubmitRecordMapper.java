package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.SubmitRecordBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitRecordMapper {
    //insert
    int addSubmitRecord(SubmitRecordBean submitRecord);

    //delete
    void deleteSubmitRecord(int submitID);

    void deleteSubmitRecordByUserID(@Param("userID") int userID);

    //update
    //insert
    int updateSubmitRecord(@Param("submitRecord") SubmitRecordBean submitRecord);

    //select
    SubmitRecordBean getSubmitRecordByID(@Param("submitID") int submitID);

    List<SubmitRecordBean> getSubmitRecordListByProblemID(@Param("problemID") int problemID);

    List<SubmitRecordBean> getSubmitRecordListByUserID(@Param("userID") int userID, @Param("start") int start, @Param("count") int count);

    List<SubmitRecordBean> getSubmitRecordListByProblemUser(@Param("problemID") int problemID, @Param("userID") int userID, @Param("start") int start, @Param("count") int count);

    List<SubmitRecordBean> getSubmitRecordListOrderedByDate(@Param("start") int start, @Param("count") int count);

    List<SubmitRecordBean> getSubmitRecordList(@Param("contestID") Integer contestID, @Param("problemID") Integer problemID, @Param("userID") Integer userID, @Param("result") String result, @Param("language") String language, @Param("start") int start, @Param("count") int count);
}

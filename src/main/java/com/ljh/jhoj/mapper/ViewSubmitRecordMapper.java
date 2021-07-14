package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.SubmitRecordBean;
import com.ljh.jhoj.model.ViewSubmitRecordBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewSubmitRecordMapper {
    //select
    SubmitRecordBean getSubmitRecordByID(@Param("submitID") Integer submitID);

    List<ViewSubmitRecordBean> getSubmitRecordListByProblemID(@Param("contestID") Integer contestID, @Param("problemID") Integer problemID);

    List<ViewSubmitRecordBean> getSubmitRecordListOrderedByDate(@Param("contestID") Integer contestID, @Param("start") Integer start, @Param("count") Integer count);

    List<Integer> getUserAcceptedProblems(@Param("userID") Integer userID);

    int getCountOnCondition(@Param("contestID") Integer contestID, @Param("problemID") Integer problemID, @Param("userName") String userName, @Param("result") String result, @Param("language") String language);

    List<ViewSubmitRecordBean> getSubmitRecordList(@Param("contestID") Integer contestID, @Param("problemID") Integer problemID, @Param("userName") String userName, @Param("result") String result, @Param("language") String language, @Param("start") Integer start, @Param("count") Integer count);

    int getCount();
}

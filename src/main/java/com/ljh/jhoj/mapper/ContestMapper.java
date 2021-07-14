package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.ContestBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestMapper {

    int addContest(@Param("contest") ContestBean contest);

    void deleteContest(@Param("contestID") Integer contestID);

    void updateContest(@Param("contest") ContestBean contest);

    ContestBean getContestByID(@Param("contestID") Integer contestID);

    void getContestByTitle(@Param("title") String title);

    List<ContestBean> getContestList(@Param("start") Integer start, @Param("count") Integer count);

    int getCount();
}

package com.ljh.jhoj.service;

import com.ljh.jhoj.model.ContestBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ljh on 18-1-1.
 */
public interface ContestService {
    int addContest(@Param("contest") ContestBean contest);

    void deleteContest(@Param("contestID") Integer contestID);

    void updateContest(@Param("contest") ContestBean contest);

    ContestBean getContestByID(@Param("contestID") Integer contestID);

    void getContestByTitle(@Param("title") String title);

    List<ContestBean> getContestList(@Param("start") Integer start, @Param("count") Integer count);

    int getCount();
}

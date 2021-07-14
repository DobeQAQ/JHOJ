package com.ljh.jhoj.service;

import com.ljh.jhoj.model.ContestProblemBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ljh on 18-1-1.
 */
public interface ContestProblemService {
    int addProblem(@Param("contestProblem") ContestProblemBean contestProblem);

    void deleteProblem(@Param("contestID") Integer contestID, @Param("innerID") String innerID);

    void deleteAllProblem(@Param("contestID") Integer contestID);

    void updateProblem(@Param("contestProblem") ContestProblemBean contestProblem);

    ContestProblemBean getContestProblem(@Param("contestID") Integer contestID, @Param("problemID") int problemID);

    List<ContestProblemBean> getContestProblemList(@Param("contestID") Integer contestID);
}

package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.ContestProblemBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestProblemMapper {
    int addProblem(@Param("contestProblem") ContestProblemBean contestProblem);

    void deleteProblem(@Param("contestID") Integer contestID, @Param("innerID") String innerID);

    void deleteAllProblem(@Param("contestID") Integer contestID);

    void updateProblem(@Param("contestProblem") ContestProblemBean contestProblem);

    ContestProblemBean getContestProblem(@Param("contestID") Integer contestID, @Param("problemID") int problemID);

    List<ContestProblemBean> getContestProblemList(@Param("contestID") Integer contestID);
}

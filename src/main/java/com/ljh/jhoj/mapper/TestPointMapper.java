package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.TestPointBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPointMapper {
    int addTestPoint(@Param("testPoint") TestPointBean testPoint);

    void deleteTestPoint(@Param("problemID") int problemID, @Param("testPointID") int testPointID);

    void updateTestPoint(@Param("testPoint") TestPointBean testPoint);

    List<TestPointBean> getTestPointList(@Param("problemID") int problemID);

    TestPointBean getTestPoint(@Param("problemID") int problemID, @Param("testPointID") int testPointID);
}

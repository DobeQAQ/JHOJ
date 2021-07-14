package com.ljh.jhoj.service;

import com.ljh.jhoj.model.TestPointBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ljh on 18-1-5.
 */
public interface TestPointService {
    int addTestPoint(@Param("testPoint") TestPointBean testPoint);

    void deleteTestPoint(@Param("problemID") int problemID, @Param("testPointID") int testPointID);

    void updateTestPoint(@Param("testPoint") TestPointBean testPoint);

    List<TestPointBean> getTestPointList(@Param("problemID") int problemID);

    TestPointBean getTestPoint(@Param("problemID") int problemID, @Param("testPointID") int testPointID);
}

package com.ljh.jhoj.datacache;

import com.ljh.jhoj.config.OjConfiguration;
import com.ljh.jhoj.mapper.ContestProblemMapper;
import com.ljh.jhoj.mapper.TestPointMapper;
import com.ljh.jhoj.model.ContestProblemBean;
import com.ljh.jhoj.model.TestPointBean;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class TestPointCacher {
    @Autowired
    private OjConfiguration configuration;
    @Autowired
    private TestPointMapper testPointMapper;
    @Autowired
    private ContestProblemMapper contestProblemMapper;

    private final Jedis jedis = new Jedis("localhost", 6379);  //指定Redis服务Host和port

    public boolean cacheTestPointOfProblem(int problemID) {
        return cacheTestPointOfProblem(problemID, 2 * 60 * 60);
    }

    public boolean cacheTestPointOfProblem(int problemID, int expire) {
        /* 数据在redis中的缓存形式(hashmap)
        p1: {
          // key  ->  value
            1.in: "input date of p1",
            1.out: "output data of p1",

            2.in: "input date of p2",
            2.out: "output data of p2",

            3.in: "input date of p3",
            3.out: "output data of p3",
        }
         */
        // 检查是否已经存在缓存数据
        String strProblemID = 1000 + problemID + "";
        if (jedis.exists(strProblemID)) {
            return true; //数据已经缓存，直接返回
        }

        // 获取题目的测试数据
        List<TestPointBean> testPointBeans = testPointMapper.getTestPointList(problemID);
        Map<String, String> testData = new TreeMap<>();
        try {
            for (TestPointBean testPoint : testPointBeans) {
                String inputDataKey = testPoint.getTestPointID() + ".in";
                String outputDataKey = testPoint.getTestPointID() + ".out";
                File stdin = new File(configuration.getTestPointHome() + "/" + strProblemID + "/" + testPoint.getInputTextPath());
                File stdout = new File(configuration.getTestPointHome() + "/" + strProblemID + "/" + testPoint.getInputTextPath());
                testData.put(inputDataKey, FileUtils.readFileToString(stdin, "utf8"));
                testData.put(outputDataKey, FileUtils.readFileToString(stdout, "utf8"));
            }
        } catch (IOException e) {
            return false;
        }

        jedis.hmset(strProblemID, testData);
        jedis.expire(strProblemID, expire); //缓存时间两个小时
        return true;
    }


    public boolean cacheTestPointOfContest(int contestID, int expire) {
        boolean result = true;
        List<ContestProblemBean> problems = contestProblemMapper.getContestProblemList(contestID);
        for (ContestProblemBean cp : problems) {
            result = result && cacheTestPointOfProblem(cp.getProblemID(), expire);
        }
        return result;
    }
}

package com.ljh.jhoj.controller;


import com.ljh.jhoj.config.OjConfiguration;
import com.ljh.jhoj.model.ProblemBean;
import com.ljh.jhoj.model.TestPointBean;
import com.ljh.jhoj.service.ProblemService;
import com.ljh.jhoj.service.TestPointService;
import com.ljh.jhoj.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class TestPointController {
    @Autowired
    private OjConfiguration configuration;

    @Autowired
    private TestPointService testPointService;

    @Autowired
    private ProblemService problemService;


    @PostMapping(value = "/add-test-point")
    public String addTestPoint(HttpServletRequest request) {
        String strProblemID = request.getParameter("inputProblemID");
        String inputData = request.getParameter("inputInputData");
        String outputData = request.getParameter("inputOutputData");

        Integer problemID = Integer.parseInt(strProblemID);

        //设置文件保存路径, 网站根目录/test-point/题目ID/[1.in|1.out]
        String testPointSavePath = configuration.getTestPointHome() + "/" + (1000 + problemID);

        System.out.println("testPointSavePath: " + testPointSavePath);

        int testPointID = Tools.saveTestPoint(testPointSavePath, inputData, outputData);
        if (testPointID == -1) {
            System.out.println("测试点文件处理出错");
        }

        TestPointBean testPointBean = new TestPointBean();
        //设置题目ID
        testPointBean.setProblemID(Integer.parseInt(strProblemID));
        //设置测试点编号
        testPointBean.setTestPointID(testPointID);
        //设置输入文本路径
        testPointBean.setInputTextPath((1000 + problemID) + "/input/" + testPointID + ".in");
        //设置输入文本长度
        testPointBean.setInputTextLength(inputData.length());
        //设置输出文本路径
        testPointBean.setOutputTextPath((1000 + problemID) + "/output/" + testPointID + ".out");
        //设置输出文本长度
        testPointBean.setOutputTextLength(outputData.length());

        System.out.println("add new test point: " + testPointBean);

        testPointService.addTestPoint(testPointBean);
        return "redirect:/test-point-list?problemID=" + problemID;
    }

    @GetMapping(value = "/delete-test-point")
    public String deleteTestPoint(HttpServletRequest request) {
        String strProblemID = request.getParameter("problemID");
        String strTestPointID = request.getParameter("testPointID");

        Integer problemID = Integer.parseInt(strProblemID);
        Integer testPointID = Integer.parseInt(strTestPointID);

        String testPointSavePath = configuration.getTestPointHome() + "/" + (1000 + problemID);

        //删除文件
        if (Tools.deleteTestPoint(testPointSavePath, testPointID)) {
            //从数据库删除记录
            testPointService.deleteTestPoint(problemID, testPointID);
        } else {
            System.out.println(String.format("题目: %d, 测试点: %d, 删除失败", problemID, testPointID));
        }

        return "redirect:/test-point-list?problemID=" + problemID;
    }


    @GetMapping(value = "/test-point-list")
    public String getTestPointList(HttpServletRequest request) {
        String strProblemID = request.getParameter("problemID");
        Integer problemID = Integer.parseInt(strProblemID);

        System.out.println("get problemID: " + strProblemID + " testpointList");

        List<TestPointBean> testPoints = testPointService.getTestPointList(problemID);
        ProblemBean problemBean = problemService.getProblemByID(problemID);


        request.setAttribute("testPointList", testPoints);
        request.setAttribute("problem", problemBean);
        return "/problem/test-point-list";
    }


    @GetMapping(value = "/show-test-point")
    public String getTestPoint(HttpServletRequest request) {
        String strProblemID = request.getParameter("problemID");
        String strTestPointID = request.getParameter("testPointID");


        Integer problemID = Integer.parseInt(strProblemID);
        Integer testPointID = Integer.parseInt(strTestPointID);


        ProblemBean problemBean = problemService.getProblemByID(problemID);


        TestPointBean testPointBean = testPointService.getTestPoint(problemID, testPointID);

        String inputText = Tools.readFileToString(configuration.getTestPointHome() + "/" + testPointBean.getInputTextPath());
        String outputText = Tools.readFileToString(configuration.getTestPointHome() + "/" + testPointBean.getOutputTextPath());

        request.setAttribute("testPoint", testPointBean);
        request.setAttribute("problem", problemBean);
        request.setAttribute("inputText", inputText);
        request.setAttribute("outputText", outputText);
        return "/problem/test-point";
    }


}

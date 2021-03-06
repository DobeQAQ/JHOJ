package com.ljh.jhoj.controller;


import com.ljh.jhoj.config.OjConfiguration;
import com.ljh.jhoj.controller.beans.MessageBean;
import com.ljh.jhoj.controller.beans.PageBean;
import com.ljh.jhoj.judge.JudgeClient;
import com.ljh.jhoj.model.ProblemBean;
import com.ljh.jhoj.service.ProblemService;
import com.ljh.jhoj.utils.Consts;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class ProblemController {

    @Autowired
    OjConfiguration configuration;

    @Autowired
    public ProblemService problemService;

    @GetMapping(value = "/problem-add")
    public String getProblemAddPage() {
        return "/problem/problem-edit";
    }

    @GetMapping(value = "/problem-edit")
    public String getProblemEditPage(@RequestParam(value = "problemID", defaultValue = "-1") Integer problemID, ModelMap mp) {
        if (problemID != -1) {
            ProblemBean problemBean = problemService.getProblemByID(problemID);
            mp.addAttribute("problem", problemBean);
            return "problem/problem-edit";
        } else {
            return "redirect:/problem-list";
        }
    }


    @PostMapping(value = "/ajax-check-problem-exist", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String checkProblemExist(@RequestParam(value = "problemID", defaultValue = "-1") Integer problemID) {
        String jsonPattern = "{\"problemID\": %s, \"exist\": %s}";
        String json = "{\"problemID\": " + problemID + ", \"exist\": false}";
        if (problemService.getProblemByID(problemID - 1000) != null) {
            json = String.format(jsonPattern, problemID, true);
        }
        System.out.println(json);
        return json;
    }

    @PostMapping(value = "/problem-add")
    public String addProblem(ProblemBean problemBean) {
        problemBean.setCreateTime(new Date().getTime());
        System.out.println("add problem: " + problemBean);
        problemService.addProblem(problemBean);
        return "redirect:/test-point-list?problemID=" + problemBean.getProblemID();
    }

    @PostMapping(value = "/problem-update")
    public String updateProblem(ProblemBean problemBean) {
        problemBean.setCreateTime(new Date().getTime());
        System.out.println("update problem: " + problemBean);
        problemService.updateProblemByID(problemBean);
        return "redirect:/problem?problemID=" + problemBean.getProblemID();
    }

    @GetMapping(value = "/problem-delete")
    public String problemDeleteGet(@RequestParam(value = "problemID", defaultValue = "-1") Integer problemID, RedirectAttributes attrs) {
        MessageBean messageBean = new MessageBean("??????", "??????", "?????????????????????!", "/problem-list", "??????????????????");
        if (problemID != -1) {
            problemService.deleteProblemByID(problemID);
            //todo ?????????????????????
            String testPointSavePath = configuration.getTestPointHome() + "/" + (1000 + problemID);
            //?????????????????????
            try {
                FileUtils.deleteDirectory(new File(testPointSavePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageBean = new MessageBean("??????", "??????", "????????????????????????!", "/problem-list", "??????????????????");
        }

        return "redirect:" + Utils.getErrorPageURL(messageBean);
    }


    @GetMapping(value = "/problem-list")
    public String getProblems(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              HttpSession session,
                              HttpServletRequest request, ModelMap mp) {
        List<ProblemBean> problemBeanList;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null) {
            problemBeanList = problemService.getProblesOrderByIDForLogin(
                    userID,
                    JudgeClient.ACCEPTED,
                    (page - 1) * Consts.COUNT_PER_PAGE,
                    Consts.COUNT_PER_PAGE);
        } else {
            problemBeanList = problemService.getProblemsOrderByID((page - 1) * Consts.COUNT_PER_PAGE, Consts.COUNT_PER_PAGE);
        }

        int recordCount = problemService.getCount();
        //??????????????????
        PageBean pageBean = Utils.getPagination(recordCount, page, request);


        mp.addAttribute("tableTitle", "????????????(" + recordCount + ")");
        mp.addAttribute("pagination", pageBean);
        mp.addAttribute("problemList", problemBeanList);
        return "/problem/problem-list";
    }


    @GetMapping(value = "/problem-search")
    public String searchProblem(@RequestParam(value = "inputProblemKeyword", defaultValue = "") String problemKeyword,
                                @RequestParam(value = "page", defaultValue = "1") Integer page, ModelMap mp, HttpServletRequest request) {
        boolean isDigital = true;
        for (char c : problemKeyword.toCharArray()) {
            if (!Character.isDigit(c)) {
                isDigital = false;
                break;
            }
        }

        int problemID = isDigital ? Integer.parseInt(problemKeyword) : 0;

        List<ProblemBean> problemBeanList = problemService.searchProblem(problemID, problemKeyword, (page - 1) * Consts.COUNT_PER_PAGE, Consts.COUNT_PER_PAGE);
        int recordCount = problemService.getSearchResultCount(problemID, problemKeyword);
        //??????????????????
        PageBean pageBean = Utils.getPagination(recordCount, page, request);


        mp.addAttribute("tableTitle", "????????????(" + recordCount + ")");
        mp.addAttribute("pagination", pageBean);
        mp.addAttribute("problemList", problemBeanList);
        return "problem/problem-list";
    }


    @GetMapping(value = "/problem")
    public String showProblem(@RequestParam(value = "problemID", defaultValue = "-1") Integer problemID,
                              HttpServletRequest request,
                              ModelMap mp,
                              RedirectAttributes attrs) {
        if (problemID != null) {
            ProblemBean problemBean = problemService.getProblemByID(problemID);
            mp.addAttribute("problem", problemBean);
            return "/problem/problem";
        } else {
            String referer = request.getHeader("referer");
            MessageBean messageBean = new MessageBean("??????", "??????", "?????????????????????!", referer != null ? referer : "/problem-list", "??????????????????");
            attrs.addFlashAttribute("message", messageBean);
            return "redirect:/error";
        }
    }

}


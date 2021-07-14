package com.ljh.jhoj.controller;

import com.ljh.jhoj.model.ContestBean;
import com.ljh.jhoj.model.DiscussBean;
import com.ljh.jhoj.service.ContestService;
import com.ljh.jhoj.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContestService contest;

    @Autowired
    private DiscussService discuss;

    @RequestMapping(value = {"/", "/index"})
    public String getIndexPage(HttpServletRequest request, ModelMap mp) throws ServletException, IOException {
        List<ContestBean> contestBeanList = contest.getContestList(0, 5);
        List<DiscussBean> discussBeanList = discuss.getDiscussTitleList(null, null, null, 0, 5);

        mp.addAttribute("latestContest", contestBeanList);
        mp.addAttribute("latestDiscuss", discussBeanList);
        return "index";
    }
}

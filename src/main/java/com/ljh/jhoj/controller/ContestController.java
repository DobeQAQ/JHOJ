package com.ljh.jhoj.controller;

import com.ljh.jhoj.controller.beans.MessageBean;
import com.ljh.jhoj.controller.beans.PageBean;
import com.ljh.jhoj.controller.beans.RankBean;
import com.ljh.jhoj.model.*;
import com.ljh.jhoj.service.*;
import com.ljh.jhoj.utils.Consts;
import com.ljh.jhoj.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ContestController {
    @Autowired
    private ProblemService problemService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ContestService contestService;
    
    @Autowired
    private ViewSubmitRecordService viewSubmitRecordService;
    
    @Autowired
    private ContestUserService contestuserService;
    
    @Autowired
    private ContestProblemService contestProblemService;
    
    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/contest-add")
    public String getContestAddPage() {
        return "/contest/contest-edit";
    }

    @GetMapping(value = "/contest-rank")
    public String getContestRank(@RequestParam(value = "contestID", defaultValue = "-1") Integer contestID,
                                 ModelMap mp) {
        if (contestID > 0) {
            ContestBean contestBean = contestService.getContestByID(contestID);
            List<ContestUserBean> users = contestuserService.getContestUserList(contestID);
            List<ContestProblemBean> problems = contestProblemService.getContestProblemList(contestID);
            List<ViewSubmitRecordBean> submits = viewSubmitRecordService.getSubmitRecordListOrderedByDate(contestID, 0, 100000);


            List<RankBean> rankList = Tools.calculateRank(contestBean, users, problems, submits);

            List<ContestProblemBean> problemOverview = Tools.getContestProblemStatistic(submits, problems);
            problemOverview.sort(new Comparator<ContestProblemBean>() {
                @Override
                public int compare(ContestProblemBean o1, ContestProblemBean o2) {
                    return o1.getInnerID().compareTo(o2.getInnerID());
                }
            });


            if (rankList.size() > 0) {
                int lastRank = 1;
                RankBean lastRankBean = rankList.get(0);
                lastRankBean.setRank(1);

                for (int i = 1; i < rankList.size(); i++) {
                    if (lastRankBean.getAC_Count() == rankList.get(0).getAC_Count() && lastRankBean.getTotalTimeConsume() == rankList.get(0).getTotalTimeConsume()) {
                        rankList.get(i).setRank(lastRank);
                    } else {
                        lastRank++;
                        rankList.get(i).setRank(lastRank);
                        lastRankBean = rankList.get(i);
                    }
                }
            }
            mp.addAttribute("contest", contestBean);
            mp.addAttribute("problemOverview", problemOverview);
            mp.addAttribute("rankList", rankList);
            return "/contest/contest-rank";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误信息", "遇到不可靠参数", "/edit-contest-problem?contestID=" + contestID, "返回");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }


    @GetMapping(value = "/contest-record-list")
    public String getContestRecordList(HttpServletRequest request, ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        Integer contestID = strContestID != null && strContestID.length() > 0 ? Integer.parseInt(strContestID) : null;

        String strPage = request.getParameter("page");
        int page = strPage != null ? Integer.parseInt(strPage) : 1;

        String userName = request.getParameter("userName");
        String strProblemID = request.getParameter("problemID");
        String result = request.getParameter("result");
        String language = request.getParameter("language");

        userName = userName != null && userName.length() > 0 ? userName : null;
        Integer problemID = strProblemID != null && strProblemID.length() > 0 ? Integer.parseInt(strProblemID) : null;
        result = result != null && result.length() > 0 ? result : null;
        language = language != null && language.length() > 0 ? language : null;

        //System.out.println(contestID + " " + problemID + " " + userName + " " + result + " " + language + " " + page);
        List<ViewSubmitRecordBean> recordList = viewSubmitRecordService.getSubmitRecordList(contestID, problemID, userName, result, language, (page - 1) * Consts.COUNT_PER_PAGE, Consts.COUNT_PER_PAGE);

        //System.out.println(recordList.size());
        //for (ViewSubmitRecordBean b : recordList) System.out.println(b);

        //获取分页信息
        int recordCount = viewSubmitRecordService.getCountOnCondition(contestID, problemID, userName, result, language);
        PageBean pageBean = Utils.getPagination(recordCount, page, request);


        List<ContestProblemBean> problemBeanList = contestProblemService.getContestProblemList(contestID);

        TreeMap<Integer, String> problemIDMaper = new TreeMap<>();
        for (ContestProblemBean p : problemBeanList) {
            problemIDMaper.put(p.getProblemID(), p.getInnerID());
        }

        ContestBean contestBean = contestService.getContestByID(contestID);
        mp.addAttribute("tableTitle", "比赛提交记录(" + recordCount + ")");
        mp.addAttribute("contest", contestBean);
        mp.addAttribute("problemIDMaper", problemIDMaper);
        mp.addAttribute("recordList", recordList);
        mp.addAttribute("pagination", pageBean);
        return "/contest/contest-submit-record";
    }

    @PostMapping(value = "/ajax-check-contest-register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String ajaxCheckContestRegister(HttpSession session,
                                           @RequestParam(value = "inputContestID", defaultValue = "-1") Integer contestID) {
        String jsonPatter = "{\"contestID\" : %s, \"userID\" :%s, \"registered\": %s}";
        String json = "";
        //{"contestID" : %s, "userID" :%s, "registered": %s}
        Object userIDObj = session.getAttribute("userID");
        int userID = userIDObj != null ? (Integer) userIDObj : -1;

        if (contestID == -1 || userID == -1) {
            return String.format(jsonPatter, -1, -1, false);
        }

        boolean registered = contestuserService.checkUserRegistered(contestID, userID);
        System.out.println("check result contestregisterd: " + registered);
        return String.format(jsonPatter, contestID, userID, registered);
    }

    @PostMapping(value = "/contest-add")
    public String addContest(@RequestParam(value = "inputContestID", defaultValue = "-1") Integer contestID,
                             ContestBean contestBean,
                             HttpSession session,
                             HttpServletRequest request) {

        String title = request.getParameter("inputTitle");
        String strStartTime = request.getParameter("inputStartTime");
        String strEndTime = request.getParameter("inputEndTime");
        String strRegisterStartTime = request.getParameter("inputRegisterStartTime");
        String strRegisterEndTime = request.getParameter("inputRegisterEndTime");
        String password = request.getParameter("inputContestPassword");
        String contestType = request.getParameter("inputContestType");
        String sponsor = request.getParameter("inputSponsor");
        String desc = request.getParameter("inputContestDesc");

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        long startTime = 0, endTime = 0;
        long registerStartTime = 0, registerEndTime = 0;
        try {
            startTime = formatter.parse(strStartTime).getTime();
            endTime = formatter.parse(strEndTime).getTime();
            registerStartTime = formatter.parse(strRegisterStartTime).getTime();
            registerEndTime = formatter.parse(strRegisterEndTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sponsor = (String) session.getAttribute("userName");

        contestBean.setTitle(title);
        contestBean.setDesc(desc);
        contestBean.setStartTime(startTime);
        contestBean.setEndTime(endTime);
        contestBean.setRegisterStartTime(registerStartTime);
        contestBean.setRegisterEndTime(registerEndTime);
        contestBean.setPassword(password);
        contestBean.setSponsor(sponsor);
        contestBean.setContestType(contestType);
        contestBean.setCreateTime(new Date().getTime());


        contestService.addContest(contestBean);
        return "redirect:/contest-problem-edit?contestID=" + contestBean.getContestID();

    }

    @PostMapping(value = "/contest-edit")
    public String updateContest(@RequestParam(value = "inputContestID", defaultValue = "-1") Integer contestID,
                                ContestBean contestBean,
                                HttpSession session,
                                HttpServletRequest request) {

        String title = request.getParameter("inputTitle");
        String strStartTime = request.getParameter("inputStartTime");
        String strEndTime = request.getParameter("inputEndTime");
        String strRegisterStartTime = request.getParameter("inputRegisterStartTime");
        String strRegisterEndTime = request.getParameter("inputRegisterEndTime");
        String password = request.getParameter("inputContestPassword");
        String contestType = request.getParameter("inputContestType");
        String sponsor = request.getParameter("inputSponsor");
        String desc = request.getParameter("inputContestDesc");

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        long startTime = 0, endTime = 0;
        long registerStartTime = 0, registerEndTime = 0;
        try {
            startTime = formatter.parse(strStartTime).getTime();
            endTime = formatter.parse(strEndTime).getTime();
            registerStartTime = formatter.parse(strRegisterStartTime).getTime();
            registerEndTime = formatter.parse(strRegisterEndTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sponsor = (String) session.getAttribute("userName");

        contestBean.setTitle(title);
        contestBean.setDesc(desc);
        contestBean.setStartTime(startTime);
        contestBean.setEndTime(endTime);
        contestBean.setRegisterStartTime(registerStartTime);
        contestBean.setRegisterEndTime(registerEndTime);
        contestBean.setPassword(password);
        contestBean.setSponsor(sponsor);
        contestBean.setContestType(contestType);
        contestBean.setCreateTime(new Date().getTime());


        contestBean.setContestID(contestID);
        contestService.updateContest(contestBean);
        return "redirect:/contest-overview?contestID=" + contestBean.getContestID();

    }

    @GetMapping(value = "/contest-delete")
    public String deleteContest(HttpServletRequest request, HttpServletResponse response) {
        String strContestID = request.getParameter("contestID");
        if (strContestID != null && strContestID.length() > 0) {
            int contestID = Integer.parseInt(strContestID);
            contestService.deleteContest(contestID);
            return "redirect:/contest-list";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误", "该比赛不存在!", "/", "回到首页");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }

    @GetMapping(value = "/contest-list")
    public String getContestList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {
        String strPage = request.getParameter("page");
        int page = strPage != null ? Integer.parseInt(strPage) : 1;


        List<ContestBean> contestBeanList = contestService.getContestList((page - 1) * Consts.COUNT_PER_PAGE, Consts.COUNT_PER_PAGE);
        int recordCount = contestService.getCount();

        //获取分页信息
        PageBean pageBean = Utils.getPagination(recordCount, page, request);

        mp.addAttribute("tableTitle", "比赛列表(" + recordCount + ")");
        mp.addAttribute("pagination", pageBean);
        mp.addAttribute("contestList", contestBeanList);
        return "/contest/contest-list";
    }


    @GetMapping(value = "/contest-overview")
    public String getContestOverview(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        Integer contestID = strContestID != null && strContestID.length() > 0 ? contestID = Integer.parseInt(strContestID) : -1;

        if (contestID > 0) {

            ContestBean contestBean = contestService.getContestByID(contestID);
            List<ContestProblemBean> problemList = contestProblemService.getContestProblemList(contestID);


            /*
             *Cookie userIDCookie = new Cookie("userID", userBean.getUserID() + "");
             *Cookie userNameCookie = new Cookie("userName", userBean.getUserName());
             */
            String strLoginedUserID = null;
            boolean isRegistered = false;

            HashMap<String, String> cookieMap = Utils.getCookieMap(request);

            if (cookieMap.containsKey("userID")) {
                int loginedUserID = Integer.parseInt(cookieMap.get("userID"));
                isRegistered = contestuserService.checkUserRegistered(contestID, loginedUserID);
            }

            int userCount = contestuserService.getContestUserCount(contestID);


            mp.addAttribute("contest", contestBean);
            mp.addAttribute("userCount", userCount);
            mp.addAttribute("problemList", problemList);
            mp.addAttribute("isRegistered", isRegistered);

            return "contest/contest-overview";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误信息", "遇到不可靠参数", "/edit-contest-problem?contestID=" + strContestID, "返回");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }

    @GetMapping(value = "/contest-detail")
    public String getContestDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                   RedirectAttributes attrs,
                                   ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        String curProblem = request.getParameter("curProblem");

        MessageBean messageBean = new MessageBean();
        messageBean.setTitle("错误");
        messageBean.setHeader("错误信息");
        messageBean.setMessage("不合法的参数");
        messageBean.setLinkText("返回");
        if (strContestID == null || strContestID.trim().length() == 0) {
            messageBean.setUrl("/");
            messageBean.setLinkText("返回主页");
        } else {

            int contestID = Integer.parseInt(strContestID);
            ContestBean contestBean = contestService.getContestByID(contestID);
            ProblemBean problemBean = null;

            List<ContestProblemBean> problemList = contestProblemService.getContestProblemList(Integer.parseInt(strContestID));
            if (curProblem != null) {
                for (ContestProblemBean t : problemList) {
                    if (t.getInnerID().equals(curProblem)) {
                        problemBean = problemService.getProblemByID(t.getProblemID());
                        break;
                    }
                }
            }

            if (problemBean == null) {//如果上面查找指定题目ID失败, 那么查找题目列表中的第一个
                problemBean = problemService.getProblemByID(problemList.get(0).getProblemID());
            }
            List<LanguageBean> languages = languageService.getLanguageList();

            boolean isRegistered = false;
            Integer userID = (Integer) session.getAttribute("userID");

            isRegistered = contestuserService.checkUserRegistered(contestID, userID);
            if (!isRegistered) {
                messageBean.setMessage("您没有资格访问该比赛的题目信息");
                messageBean.setUrl("/contest-overview?contestID=" + contestID);
                return "redirect:" + Utils.getErrorPageURL(messageBean);
            }


            mp.addAttribute("contest", contestBean);
            mp.addAttribute("problemList", problemList);
            mp.addAttribute("problem", problemBean);
            mp.addAttribute("languages", languages);
            mp.addAttribute("isRegistered", isRegistered);
            mp.addAttribute("user", userService.getUserByID(userID));
            return "/contest/contest-detail";
        }
        return "redirect:" + Utils.getErrorPageURL(messageBean);
    }

    @GetMapping(value = "/contest-edit")
    public String editContestGet(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        if (strContestID != null && strContestID.length() > 0) {
            int contestID = Integer.parseInt(strContestID);
            ContestBean contestBean = contestService.getContestByID(contestID);
            List<ContestProblemBean> problemList = contestProblemService.getContestProblemList(contestID);

            mp.addAttribute("contest", contestBean);
            mp.addAttribute("problemList", problemList);
            return "/contest/contest-edit.html";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误信息", "遇到不可靠参数", "/edit-contest-problem?contestID=" + strContestID, "返回");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }

    @GetMapping(value = "/contest-problem-delete")
    public String deleteContestProblemGet(HttpServletRequest request, HttpServletResponse response) {
        String strContestID = request.getParameter("contestID");
        String innerID = request.getParameter("innerID");
        if (strContestID != null && strContestID.length() > 0) {
            int contestID = Integer.parseInt(strContestID);
            contestProblemService.deleteProblem(contestID, innerID);
            return "redirect:/contest-problem-edit?contestID=" + contestID;
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误信息", "遇到不可靠参数", "/edit-contest-problem?contestID=" + strContestID, "返回");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }

    @GetMapping(value = "/contest-problem-edit")
    public String editContestProblemGet(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        if (strContestID != null && strContestID.length() > 0) {
            int contestID = Integer.parseInt(strContestID);

            contestProblemService.getContestProblemList(contestID);
            ContestBean contestBean = contestService.getContestByID(contestID);


            mp.addAttribute("problemList", contestProblemService.getContestProblemList(contestID));
            mp.addAttribute("contest", contestBean);
            return "/contest/contest-problem-edit";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误", "该比赛不存在!", "/", "回到首页");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }

    }

    @PostMapping(value = "/contest-problem-edit")
    public String editContestProblemPost(HttpServletRequest request, HttpServletResponse response) {
        //添加题目的验证有js完成, 此处不做验证
        String strContestID = request.getParameter("inputContestID");
        String innerID = request.getParameter("inputInnerID");
        String strProblemID = request.getParameter("inputProblemID");

        int contestID = Integer.parseInt(strContestID);
        int problemID = Integer.parseInt(strProblemID);

        ContestProblemBean contestProblemBean = new ContestProblemBean();
        contestProblemBean.setContestID(contestID);
        contestProblemBean.setInnerID(innerID.trim().toUpperCase());
        contestProblemBean.setProblemID(problemID - 1000);//用户输入的是加1000后的题目号码
        contestProblemBean.setAccepted(0);
        contestProblemBean.setSubmitted(0);

        contestProblemBean.setTitle(problemService.getProblemByID(problemID - 1000).getTitle());//获取并设置题目标题

        contestProblemService.addProblem(contestProblemBean);
        return "redirect:/contest-problem-edit?contestID=" + contestID;
    }

    @RequestMapping(value = "/contest-register")
    public String registerContest(HttpServletRequest request, HttpServletResponse response) {
        String strContestID = request.getParameter("contestID");
        int contestID = strContestID != null && strContestID.length() > 0 ? Integer.parseInt(strContestID) : -1;

        HashMap<String, String> cookieMap = Utils.getCookieMap(request);

        MessageBean messageBean = new MessageBean();
        messageBean.setTitle("错误");
        messageBean.setHeader("错误信息");

        int flag = 0;
        if (contestID == -1) {
            flag++;
            messageBean.setMessage("比赛ID错误");
            messageBean.setUrl("/contest-list");
            messageBean.setLinkText("返回比赛列表");
        }

        if (!cookieMap.containsKey("userID")) {
            if (flag == 0) {
                messageBean.setMessage("请登录系统后再注册比赛");
                messageBean.setUrl("/");
                messageBean.setLinkText("返回首页");
            }
            flag++;
        }

        if (flag != 0) {
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        } else {
            int userID = Integer.parseInt(cookieMap.get("userID"));

            String password = contestService.getContestByID(contestID).getPassword();
            if (password.length() == 0) {/*开放比赛只检查是否已经报名, 防止主键重复错误*/
                if (!contestuserService.checkUserRegistered(contestID, userID)) {
                    contestuserService.addUser(new ContestUserBean(contestID, userID, cookieMap.get("userName")));
                }
            } else {/*加密比赛检查密码*/
                String userInputPassword = request.getParameter("inputContestPassword");
                if (userInputPassword != null && !contestuserService.checkUserRegistered(contestID, userID) && userInputPassword.equals(password)) {
                    contestuserService.addUser(new ContestUserBean(contestID, userID, cookieMap.get("userName")));
                } else {
                    messageBean.setMessage("您输入的密码不正确!");
                    messageBean.setUrl("/contest-overview?contestID=" + contestID);
                    messageBean.setLinkText("返回");
                    return "redirect:" + Utils.getErrorPageURL(messageBean);
                }
            }
            return "redirect:/contest-overview?contestID=" + contestID;
        }
    }


    @GetMapping(value = "/contest-user-list")
    public String getContestUserList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {
        String strContestID = request.getParameter("contestID");
        int contestID = strContestID != null && strContestID.length() > 0 ? Integer.parseInt(strContestID) : -1;
        if (contestID != -1) {
            List<ContestUserBean> userList = contestuserService.getContestUserList(contestID);
            ContestBean contestBean = contestService.getContestByID(contestID);
            mp.addAttribute("contest", contestBean);
            mp.addAttribute("userList", userList);
            return "/contest/contest-user-list";
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误", "比赛ID不正确", "", "");
            return "redirect:" + Utils.getErrorPageURL(messageBean);
        }
    }


    @GetMapping(value = "/contest-user-delete")
    public String deleteContestUser(@RequestParam(value = "contestID", defaultValue = "-1") Integer contestID,
                                    @RequestParam(value = "userID", defaultValue = "-1") Integer userID,
                                    RedirectAttributes attrs) {

        if (contestID != -1 && userID != -1) {
            contestuserService.deleteUser(contestID, userID);
            return "redirect:/contest-user-list?contestID=" + contestID;
        } else {
            MessageBean messageBean = new MessageBean("错误", "错误", "比赛ID或者用户ID不正确!", "/contest-user-list?contesetID=" + contestID, "回到首页");
            attrs.addFlashAttribute("message", messageBean);
            return "/error";
        }
    }

}

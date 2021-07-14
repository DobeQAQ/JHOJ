package com.ljh.jhoj.controller;


import com.ljh.jhoj.config.OjConfiguration;
import com.ljh.jhoj.controller.beans.MessageBean;

import com.ljh.jhoj.model.UserBean;
import com.ljh.jhoj.service.UserService;
import com.ljh.jhoj.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    OjConfiguration configuration;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin")
    public String getAdminPage() {
        return "/admin/admin";
    }

    @GetMapping(value = "/configuration")
    public String getConfigurationPage(ModelMap mp) {
        mp.addAttribute("configuration", configuration);
        //todo 页面显示配置信息
        return "/admin/configuration";
    }

    @GetMapping(value = "/send-email")
    public String getSendEmailPage(@RequestParam("toAll") String toAll,
                                   @RequestParam(value = "userID", defaultValue = "-1") Integer userID,
                                   ModelMap mp) {
        if (toAll != null) {
            List<String> emailList = userService.getUserEmailList();
            mp.addAttribute("emailList", emailList);
            return "/admin/send-email";
        } else {
            if (userID != -1) {
                UserBean userBean = userService.getUserByID(userID);
                mp.addAttribute("user", userBean);
                return "/admin/send-email";
            } else {
                return "redirect:" + Utils.getErrorPageURL(new MessageBean("消息", "消息", "邮件发送成功!", "", ""));
            }
        }
    }


    @PostMapping(value = "/send-email")
    public String sendMailPost(@RequestParam("inputEmailAddress") String strEmailAddress,
                               @RequestParam("inputContent") String content,
                               @RequestParam("inputSubject") String subject) {
        strEmailAddress = strEmailAddress.replace("[", "").replace("]", "");
        String[] emailAddresses = strEmailAddress.replace("[", "").replace("]", "").split(",");

        MessageBean messageBean;

        //System.out.println(emailAddresses.toString() + " subject " + content);
        if (SendMail.sendMail(emailAddresses, subject, content)) {
            messageBean = new MessageBean("消息", "消息", "邮件发送成功!", "", "");
        } else {
            messageBean = new MessageBean("消息", "消息", "邮件发送失败!", "", "");
        }
        return "redirect:" + Utils.getErrorPageURL(messageBean);
    }

}

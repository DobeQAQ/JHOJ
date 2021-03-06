package com.ljh.jhoj.controller;


import com.ljh.jhoj.controller.beans.MessageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MessageController {
    @GetMapping(value = "/message")
    public String doGet(HttpServletRequest request, ModelMap mp) {
        String title = request.getParameter("title");
        String header = request.getParameter("header");
        String message = request.getParameter("message");
        String url = request.getParameter("url");
        String linkText = request.getParameter("linkText");

        MessageBean messageBean = new MessageBean(title, header, message, url, linkText);
        mp.addAttribute("message", messageBean);
        return "information";
    }
}

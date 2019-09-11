package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/oauth")
public class LoginRedirect {
    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(String Form, Model model){
        model.addAttribute("form",Form);
        return "login";
    }
}

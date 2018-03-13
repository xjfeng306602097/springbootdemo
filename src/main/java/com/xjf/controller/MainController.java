package com.xjf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/1/24.
 */
@Controller
public class MainController {

    @RequestMapping("/index")
    public String toIndex() {
        return "pages/index";
    }

    @RequestMapping("/love")
    public String toLove() {
        return "pages/love";
    }

    @RequestMapping("/mylove")
    public String mylove() {
        return "pages/mylove";
    }

}

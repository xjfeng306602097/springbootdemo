package com.xjf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/24.
 */
@RestController
public class DemoController {

    @RequestMapping("/")
    String home() {
        return "Hello World";
    }

    @RequestMapping("/demo")
    String demo() {
        return "demo";
    }

}

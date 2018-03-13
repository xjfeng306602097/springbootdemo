package com.xjf.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjf.constant.CommonCode;
import com.xjf.constant.CommonConstant;
import com.xjf.constant.ControllerPagesConstant;
import com.xjf.constant.ControllerPrefixConstant;
import com.xjf.dto.LoginResult;
import com.xjf.repository.User;
import com.xjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());

    @ResponseBody
    @RequestMapping(value = ControllerPrefixConstant.DO_LOGIN)
    public LoginResult doLogin(@RequestBody JSONObject param, HttpServletRequest request) {
        LoginResult loginResult = new LoginResult();
        HttpSession session = request.getSession();
        String userId = (String)param.get("userId");
        String password = DigestUtils.md5DigestAsHex((param.get("password") + CommonConstant.SALT).getBytes());
        User result = userService.getUserById(userId);
        if (result != null) {
            if (password.equals(result.gettPassword())) {
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute("user", JSONObject.toJSON(result));
                loginResult.setUserName(result.gettUsername());
                loginResult.setStatus(CommonCode.SUCCESS);
                loginResult.setMessage(CommonConstant.LOGIN_SUCCESS);
            } else {
                loginResult.setMessage(CommonConstant.LOGIN_FAIL);
                loginResult.setStatus(CommonCode.FAIL);
            }
        } else {
            loginResult.setMessage(CommonConstant.LOGIN_FAIL);
            loginResult.setStatus(CommonCode.USER_NOT_EXIST);
        }
        return loginResult;
    }

    @RequestMapping(value = ControllerPrefixConstant.DO_LOGOUT, method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:login";
    }

    @RequestMapping(value = ControllerPrefixConstant.LOGIN, method = RequestMethod.GET)
    public String login() {
        return ControllerPagesConstant.LOGIN;
    }

    @RequestMapping(value="/testLogin")
    public String doLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        session.setAttribute("sessionId", sessionId);
        return "pages/main";
    }

    @RequestMapping(value = "/main")
    public String toMain(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        if (sessionId != null) {
            return "pages/main";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(value = "/testLogout")
    public String testLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("sessionId");
        session.invalidate();
        return "redirect:/user/login";
    }

}

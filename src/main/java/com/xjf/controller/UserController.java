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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

}

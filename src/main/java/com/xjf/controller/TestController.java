package com.xjf.controller;

import com.xjf.exception.MyException;
import com.xjf.repository.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    static Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(HttpServletRequest request, @RequestBody User user) {
        users.put(user.gettUserid(), user);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable String id, @RequestBody User user) {
        User u = users.get(id);
        u.settUsername(user.gettUsername());
        u.settCreatedate(user.gettCreatedate());
        u.settSex(user.gettSex());
        u.settPassword(user.gettPassword());
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        return users.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        users.remove(id);
        return "success";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String throwExpcetion() throws MyException {
        throw new MyException("发生错误");
    }
}

package com.liu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {

        List<User> userList = Arrays.asList(
                new User("听风", "123456", "男", 23),
                new User("安琪儿", "123456", "女", 22)
        );

        return userList;
    }


}

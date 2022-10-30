package com.test;

import com.pojo.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null,"zhangsan","3333","zhangsan@qq.com"));
        userService.registUser(new User(null,"wangwu","5555","wangwu@qq.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null,"wangwu","5555",null)));
    }

    @Test
    public void existsUsername() {
    }
}
package com.cdeo.usercenter.controller;

import com.cdeo.usercenter.Model.domain.User;
import com.cdeo.usercenter.Model.request.UserLoginRequest;
import com.cdeo.usercenter.Model.request.UserRegisterRequest;
import com.cdeo.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    /**
     * 注册成功返回用户信息
     *
     * @param userRegisterRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/registerUser")
    public Long registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyEmpty(userAccount, password, checkPassword)) {
            return null;
        }
        return userService.registerUser(userAccount, password, checkPassword);
    }

    /**
     * user登录
     * @param loginRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loginUser")
    public User loginUser(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        String userAccount = loginRequest.getUserAccount();
        String password = loginRequest.getPassword();

        if (StringUtils.isAnyEmpty(userAccount, password)) {
            return null;
        }
        return userService.loginUser(userAccount, password, request);
    }

    @PostMapping("/deleteUser")
    public Boolean deleteUser(@RequestBody Long userId, HttpServletRequest httpServletRequest) {

        return userService.deleteUser(userId, httpServletRequest);
    }

    @PostMapping("/search")
    public List<User> search(String username, HttpServletRequest httpServletRequest) {
        return userService.searchUserList(username, httpServletRequest);
    }

}

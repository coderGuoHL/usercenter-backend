package com.cdeo.usercenter.controller;

import com.cdeo.usercenter.Model.domain.User;
import com.cdeo.usercenter.Model.request.UserLoginRequest;
import com.cdeo.usercenter.Model.request.UserRegisterRequest;
import com.cdeo.usercenter.common.BaseResponse;
import com.cdeo.usercenter.common.ResultUtils;
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
    public BaseResponse<Long> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyEmpty(userAccount, password, checkPassword)) {
            return null;
        }
        return ResultUtils.success(userService.registerUser(userAccount, password, checkPassword));
    }

    /**
     * user登录
     * @param loginRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loginUser")
    public BaseResponse<User> loginUser(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        String userAccount = loginRequest.getUserAccount();
        String password = loginRequest.getPassword();

        if (StringUtils.isAnyEmpty(userAccount, password)) {
            return null;
        }
        return ResultUtils.success(userService.loginUser(userAccount, password, request));
    }

    @PostMapping("/deleteUser")
    public BaseResponse<Boolean> deleteUser(@RequestBody Long userId, HttpServletRequest httpServletRequest) {

        return ResultUtils.success(userService.deleteUser(userId, httpServletRequest));
    }

    @PostMapping("/search")
    public BaseResponse<List<User>> search(String username, HttpServletRequest httpServletRequest) {
        return ResultUtils.success(userService.searchUserList(username, httpServletRequest));
    }

    @ResponseBody
    @PostMapping("/currentUser")
    public BaseResponse<User> currentUser(HttpServletRequest request) {
        return ResultUtils.success(userService.getCurrentUser(request));
    }

    @PostMapping("/outLogin")
    public BaseResponse<Boolean> outLogin(HttpServletRequest request) {
        return ResultUtils.success(userService.outLogin(request));
    }


}

package com.cdeo.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdeo.usercenter.Model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Coder guo
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2023-09-03 20:20:19
*/
public interface UserService extends IService<User> {
    long registerUser(String userAccount, String password, String checkPassword);

    User loginUser(String userAccount, String password, HttpServletRequest request);

    Boolean deleteUser(Long userId, HttpServletRequest request);

    List<User> searchUserList(String username, HttpServletRequest request);

}

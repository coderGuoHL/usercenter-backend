package com.cdeo.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdeo.usercenter.Model.domain.User;
import com.cdeo.usercenter.common.BusinessException;
import com.cdeo.usercenter.common.ErrorCode;
import com.cdeo.usercenter.service.UserService;
import com.cdeo.usercenter.mapper.UserMapper;
import com.cdeo.usercenter.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cdeo.usercenter.util.UserConst.SALT;
import static com.cdeo.usercenter.util.UserConst.SESSION_LOGIN_USER;

/**
* @author Coder guo
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2023-09-03 20:20:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {



    /**
     *
     * @param userAccount 用户账户
     * @param password 用户密码
     * @param checkPassword 用户二次输入密码
     * @return 用户id
     */
    @Override
    public long registerUser(String userAccount, String password, String checkPassword) {
        // 1、非空
        if (StringUtils.isAnyEmpty(userAccount, password, checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_PARAM_ERROR);
        }

        // 2. 账户长度 **不小于** 4 位
        if (userAccount.length() < 2) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 3. 密码就 **不小于** 8 位吧
        if (password.length() < 4 || checkPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 用户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 密码和第二次密码不相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 密码不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 密码加密
        String inWritingCode = "cdeo" + password;
        String encryptPassword = DigestUtils.md5DigestAsHex(inWritingCode.getBytes());

        // 插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptPassword);
        this.save(user);

        if (user.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        return user.getId();
    }

    /**
     * 1、校验数据是否合法
     * 2、校验密码输入是否正确
     * 3、用户信息脱敏
     * 4、记录用户信息到session中
     * 5、返回脱敏后的用户信息
     * @param userAccount
     * @param password
     * @return
     */
    @Override
    public User loginUser(String userAccount, String password, HttpServletRequest request) {
        // 1、非空
        if (StringUtils.isAnyEmpty(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 2. 账户长度 **不小于** 2 位
        if (userAccount.length() < 2) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 3. 密码就 **不小于** 4 位吧
        if (password.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 用户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 查询用户和密码是否匹配
        String inWritingCode = SALT + password;
        String encryptPassword = DigestUtils.md5DigestAsHex(inWritingCode.getBytes());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("password", encryptPassword);

        User user = this.getOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 用户信息脱敏
        User handledUser = getSafeUser(user);

        // 设置信息到session中
        request.getSession().setAttribute(SESSION_LOGIN_USER, handledUser);

        return handledUser;
    }

    @Override
    public Boolean deleteUser(Long userId, HttpServletRequest request) {
        User sessionUse = (User) request.getSession().getAttribute(SESSION_LOGIN_USER);
        if (!UserUtil.isAdmin(sessionUse)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        return this.removeById(userId);
    }

    /**
     * 只输入用户姓名进行查询，允许为空，为空时查询所有
     * @param username
     * @param request
     * @return
     */
    @Override
    public List<User> searchUserList(String username, HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute(SESSION_LOGIN_USER);

        if (!UserUtil.isAdmin(sessionUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(username)) {
            queryWrapper.like("username", username);
        }

        List<User> users = this.list(queryWrapper);

        return users;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_LOGIN_USER);

        if (user == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        Long id = user.getId();
        User dbUser = this.getById(id);
        return this.getSafeUser(dbUser);
    }

    @Override
    public User getSafeUser(User user) {
        if (user == null) {
            return null;
        }

        User handledUser = new User();
        handledUser.setId(user.getId());
        handledUser.setUserAccount(user.getUserAccount());
        handledUser.setUsername(user.getUsername());
        handledUser.setAvatarUrl(user.getAvatarUrl());
        handledUser.setGender(user.getGender());
        handledUser.setUserRole(user.getUserRole());
        handledUser.setUserStatus(user.getUserStatus());
        handledUser.setEmail(user.getEmail());
        handledUser.setUserStatus(user.getUserStatus());
        handledUser.setCreateTime(new Date());
        handledUser.setUpdateTime(new Date());

        return handledUser;
    }

    @Override
    public Boolean outLogin(HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_LOGIN_USER, null);
        return true;
    }
}





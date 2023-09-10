package com.cdeo.usercenter.service;
import java.util.Date;

import com.cdeo.usercenter.Model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void testAdd() {
        User user = new User();
        user.setId(0L);
        user.setUserAccount("cdeo");
        user.setUsername("cdeo");
        user.setAvatarUrl("https://www.baidu.com/img/flexible/logo/pc/result.png");
        user.setGender(0);
        user.setPassword("1313");
        user.setEmail("4123131");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean save = userService.save(user);

        assertTrue(save);
        System.out.println(user);
    }


    // 1. 用户在前端输入账户和密码、以及校验码（todo）
    // 2. 校验用户的账户、密码、校验密码，是否符合要求
    //    1. 非空
    //    2. 账户长度 **不小于** 4 位
    //    3. 密码就 **不小于** 8 位吧
    //    4. 账户不能重复
    //    5. 账户不包含特殊字符
    //    6. 密码和校验密码相同
    // 3. 对密码进行加密（密码千万不要直接以明文存储到数据库中）
    // 4. 向数据库插入用户数据
    @Test
    void registerUser() {
        String userAccount = "cdeoHero";
        String password = "12356789";
        String checkPassword = "12356789";
        long result = -1;

        userAccount = "";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);


        userAccount = "02";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);

        password = "1234";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);

        password = "12345678";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);

        userAccount = "cdeo hero *";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);

        userAccount = "cdeoHero";
        password = "12356789";
        checkPassword = "12356789";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result == -1);

        userAccount = "cdeoHero123";
        password = "12356789";
        checkPassword = "12356789";
        result = userService.registerUser(userAccount, password, checkPassword);
        Assertions.assertTrue(result > 0);
    }
}
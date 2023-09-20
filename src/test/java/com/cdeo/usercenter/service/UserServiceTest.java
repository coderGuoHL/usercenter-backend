package com.cdeo.usercenter.service;
import java.util.Date;

import com.cdeo.usercenter.model.domain.User;
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


    // 1. �û���ǰ�������˻������롢�Լ�У���루todo��
    // 2. У���û����˻������롢У�����룬�Ƿ����Ҫ��
    //    1. �ǿ�
    //    2. �˻����� **��С��** 4 λ
    //    3. ����� **��С��** 8 λ��
    //    4. �˻������ظ�
    //    5. �˻������������ַ�
    //    6. �����У��������ͬ
    // 3. ��������м��ܣ�����ǧ��Ҫֱ�������Ĵ洢�����ݿ��У�
    // 4. �����ݿ�����û�����
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
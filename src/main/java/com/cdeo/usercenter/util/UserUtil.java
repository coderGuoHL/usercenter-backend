package com.cdeo.usercenter.util;

import com.cdeo.usercenter.model.domain.User;

public class UserUtil {
    public static Boolean isAdmin(User user) {
        return user != null && UserConst.ADMIN_USER_ROLE.equals(user.getUserRole());
    }
}

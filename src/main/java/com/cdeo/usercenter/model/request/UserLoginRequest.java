package com.cdeo.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -7961600645895364801L;

    private String userAccount;

    private String password;
}

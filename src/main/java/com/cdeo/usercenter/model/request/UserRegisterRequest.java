package com.cdeo.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -4786909540608874542L;

    private String userAccount;

    private String password;

    private String checkPassword;
}

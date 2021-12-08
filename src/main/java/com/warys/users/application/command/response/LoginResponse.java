package com.warys.users.application.command.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class LoginResponse implements Serializable {
    private String token;
    private String id;
    private String username;
    private String email;
}

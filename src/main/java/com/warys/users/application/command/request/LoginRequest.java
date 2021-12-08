package com.warys.users.application.command.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class LoginRequest {
    @NotNull
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 8, max = 100, message = "password must be between 8 and 100 characters")
    private String password;
}

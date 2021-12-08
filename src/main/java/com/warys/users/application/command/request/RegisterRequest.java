package com.warys.users.application.command.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    @Size(min = 5, max = 15, message = "username must be between 5 and 15 characters")
    private String username;
    @NotNull
    @Email(message = "Email should be valid")
    private String email;
    private String firstName;
    private String lastName;

}

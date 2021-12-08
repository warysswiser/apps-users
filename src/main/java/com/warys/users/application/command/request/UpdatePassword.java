package com.warys.users.application.command.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePassword {
    @NotBlank
    @Size(min = 8, max = 100, message = "password must be between 8 and 100 characters")
    private String password;
}

package com.warys.users.application.rest;

import com.warys.users.application.command.request.LoginRequest;
import com.warys.users.application.command.request.RegisterRequest;
import com.warys.users.application.command.response.LoginResponse;
import com.warys.users.application.command.response.RegisterResponse;
import com.warys.users.application.response.ApiResponse;
import com.warys.users.domain.service.user.AuthenticationService;
import com.warys.users.infrastructure.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.warys.users.application.response.ApiResponse.created;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicUsersController {

    private final AuthenticationService authentication;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws ApiException {
        LoginResponse response = authentication.login(request.getEmail(), request.getPassword());
        return created(response);
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) throws ApiException {
        RegisterResponse response = authentication.register(request);
        return created(response);
    }
}


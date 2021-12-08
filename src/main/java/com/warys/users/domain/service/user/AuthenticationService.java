package com.warys.users.domain.service.user;

import com.warys.users.application.command.request.RegisterRequest;
import com.warys.users.application.command.response.LoginResponse;
import com.warys.users.application.command.response.RegisterResponse;
import com.warys.users.domain.model.user.User;
import com.warys.users.infrastructure.exception.ApiException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AuthenticationService extends UserDetailsService {

    LoginResponse login(String email, String password) throws ApiException;

    RegisterResponse register(RegisterRequest user) throws ApiException;

    Optional<User> findByToken(String token);
}
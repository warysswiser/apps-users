package com.warys.users.application.rest.secured;

import com.warys.users.application.command.request.UpdatePassword;
import com.warys.users.application.response.ApiResponse;
import com.warys.users.domain.model.user.Session;
import com.warys.users.domain.model.user.User;
import com.warys.users.domain.service.user.UserService;
import com.warys.users.infrastructure.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.warys.users.application.response.ApiResponse.ok;

@AllArgsConstructor
@RestController
@RequestMapping("/me")
public final class UserController {

    private final UserService authenticatedUserService;

    @GetMapping("")
    public ApiResponse<User> getMe(@AuthenticationPrincipal final Session user) throws ApiException {
        return ok(authenticatedUserService.getMe(user));
    }

    @PutMapping("")
    public ApiResponse<User> updateMe
            (@AuthenticationPrincipal final Session user, @RequestBody @Valid final User newUser) throws ApiException {
        return ok(authenticatedUserService.updateUser(user, newUser));
    }

    @PatchMapping("")
    public ApiResponse<User> partialUpdateMe
            (@AuthenticationPrincipal final Session user, @RequestBody @Valid final User partialNewUser) throws ApiException {
        User updatedUser = authenticatedUserService.partialUpdateUser(user, partialNewUser);
        return ok(updatedUser);
    }

    @PutMapping("/password")
    public ApiResponse<String> updatePassword
            (@AuthenticationPrincipal final Session user, @RequestBody @Valid final UpdatePassword password) throws ApiException {
        authenticatedUserService.updatePassword(user, password);
        return ok("Password updated");
    }
}
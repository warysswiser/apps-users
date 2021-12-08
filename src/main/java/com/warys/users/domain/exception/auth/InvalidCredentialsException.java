package com.warys.users.domain.exception.auth;

import com.warys.users.infrastructure.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException(String s) {
        super(s);
    }
}

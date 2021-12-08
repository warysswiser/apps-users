package com.warys.users.domain.service.user;

import com.warys.users.application.command.request.UpdatePassword;
import com.warys.users.domain.model.user.User;
import com.warys.users.domain.model.user.Session;
import com.warys.users.domain.service.MyCrudService;
import com.warys.users.infrastructure.exception.ApiException;

import java.util.List;

public interface UserService extends MyCrudService<User, User> {

    default User updateUser(Session user, User newUser) throws ApiException {
        throw new UnsupportedOperationException();
    }

    default User partialUpdateUser(Session user, User partialNewUser) throws ApiException {
        throw new UnsupportedOperationException();
    }

    default User getUserByCredentials(String email, String password) throws ApiException {
        throw new UnsupportedOperationException();
    }

    default void checkUserEmail(String email) throws ApiException {
        throw new UnsupportedOperationException();
    }

    @Override
    default User retrieve(Session me, String itemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    default User create(Session me, User payload) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void remove(Session me, String itemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    default User update(Session me, String itemId, User payload) {
        throw new UnsupportedOperationException();
    }

    @Override
    default User partialUpdate(Session me, String itemId, User partialPayload) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<User> getAll(Session me) {
        throw new UnsupportedOperationException();
    }

    default void updatePassword(Session me, UpdatePassword newPassword) throws ApiException {
        throw new UnsupportedOperationException();
    }

    default User getMe(Session user) throws ApiException {
        throw new UnsupportedOperationException();
    }
}

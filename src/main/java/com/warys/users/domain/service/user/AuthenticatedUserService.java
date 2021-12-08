package com.warys.users.domain.service.user;

import com.warys.users.application.command.request.UpdatePassword;
import com.warys.users.domain.model.user.User;
import com.warys.users.domain.common.mapper.BeanMapper;
import com.warys.users.domain.common.mapper.UserMapper;
import com.warys.users.domain.common.validator.UserValidator;
import com.warys.users.domain.model.user.Session;
import com.warys.users.infrastructure.repository.mongo.entity.UserDocument;
import com.warys.users.infrastructure.repository.mongo.UserRepository;
import com.warys.users.infrastructure.exception.ApiException;
import com.warys.users.domain.exception.ElementNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.warys.users.domain.common.util.Patcher.patch;

@Service
public class AuthenticatedUserService implements UserService {

    private final UserRepository userRepository;
    private final BeanMapper<UserDocument, User> mapper;

    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new UserMapper();
    }

    @Override
    public User getMe(Session me) throws ElementNotFoundException {
        return userRepository
                .findById(me.getId())
                .map(mapper.mapToInput())
                .orElseThrow(() -> new ElementNotFoundException("Can not find user"));
    }

    @Override
    public User updateUser(Session user, User newUser) throws ElementNotFoundException {
        Objects.requireNonNull(newUser, "Null user set");
        UserValidator.of(newUser).validateForUpdate();

        final UserDocument userToUpdate = getUserById(user);
        patch(newUser, userToUpdate);

        return Optional.of(userRepository.save(userToUpdate)).map(mapper.mapToInput()).orElseThrow();
    }

    @Override
    public User partialUpdateUser(Session user, User partialItem) throws ElementNotFoundException {
        Objects.requireNonNull(partialItem, "Null user set");

        final UserDocument userToUpdate = getUserById(user);
        patch(partialItem, userToUpdate);

        return Optional.of(userRepository.save(userToUpdate)).map(mapper.mapToInput()).orElseThrow();
    }

    @Override
    public void updatePassword(Session me, UpdatePassword newPassword) throws ApiException {
        Objects.requireNonNull(newPassword, "Null password set");
        final UserDocument userToUpdate = getUserById(me);

        userToUpdate.setPassword(newPassword.getPassword());

        userRepository.save(userToUpdate);
    }

    private UserDocument getUserById(Session user) throws ElementNotFoundException {
        return userRepository
                .findById(user.getId())
                .orElseThrow(() -> new ElementNotFoundException("Can not find user"));
    }

}

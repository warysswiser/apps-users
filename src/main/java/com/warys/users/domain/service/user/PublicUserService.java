package com.warys.users.domain.service.user;

import com.warys.users.domain.exception.InconsistentElementException;
import com.warys.users.domain.model.user.User;
import com.warys.users.domain.common.mapper.BeanMapper;
import com.warys.users.domain.common.mapper.UserMapper;
import com.warys.users.infrastructure.repository.mongo.entity.UserDocument;
import com.warys.users.infrastructure.repository.mongo.UserRepository;
import com.warys.users.domain.exception.DuplicatedInformationException;
import com.warys.users.domain.exception.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class PublicUserService implements UserService {

    private final UserRepository userRepository;
    private final BeanMapper<UserDocument, User> mapper;

    public PublicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        mapper = new UserMapper();
    }

    @Override
    public User getUserByCredentials(final String email, final String password) throws InvalidCredentialsException {
        return userRepository.findByEmailAndPassword(email, password)
                .map(mapper.mapToInput())
                .orElseThrow(() -> new InvalidCredentialsException("Unknown user for given credentials"));
    }

    @Override
    public void checkUserEmail(final String email) throws DuplicatedInformationException, InconsistentElementException {
        if(email == null) {
            throw new InconsistentElementException("[email] : must not be null; ");
        }
        if (userRepository.findByEmail(email).isPresent())
            throw new DuplicatedInformationException("UserDocument already exists for email : " + email);
    }
}

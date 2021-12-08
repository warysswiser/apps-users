package com.warys.users.domain.service.user;

import com.warys.users.application.command.request.RegisterRequest;
import com.warys.users.application.command.response.LoginResponse;
import com.warys.users.application.command.response.RegisterResponse;
import com.warys.users.domain.exception.auth.InvalidCredentialsException;
import com.warys.users.domain.model.builder.UserBuilder;
import com.warys.users.domain.model.user.User;
import com.warys.users.infrastructure.exception.ApiException;
import com.warys.users.infrastructure.repository.mongo.UserRepository;
import com.warys.users.infrastructure.repository.mongo.entity.UserDocument;
import com.warys.users.infrastructure.spi.auth.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class UserAuthenticationServiceShould {

    private static final String VALID_EMAIL = "email";
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_ID = "VALID_ID";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoZW50aWNhdGlvbiB0b2tlbiIsImV4cCI6MjUwOTU2MTYwOCwidXNlcklkIjoiVkFMSURfSUQifQ.YdMa2YHaB8ubhEWDEGCwWMr2qDhIxWREQxYkGG1LCfx7AroeAnLua1McYIcJ4L6OX398CnOR-15u7wpRs7WibQ";
    private PublicUserService userService = mock(PublicUserService.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private TokenProvider tokenProvider = mock(TokenProvider.class);
    private AuthenticationService tested = new UserAuthenticationService(userService, userRepository, tokenProvider);

    private static final UserDocument VALID_USER = new UserBuilder().with(
            o -> {
                o.id = VALID_ID;
                o.email = VALID_EMAIL;
                o.password = VALID_PASSWORD;
                o.creationDate = LocalDateTime.now();
            }
    ).build();

    @BeforeEach
    void setUp(){
        when(tokenProvider.getSecret()).thenReturn("ThisIsASecret");
        when(tokenProvider.generateFrom(any(User.class))).thenReturn(VALID_TOKEN);
    }

    @Test
    void login() throws ApiException {

        when(userService.getUserByCredentials(VALID_EMAIL, VALID_PASSWORD)).thenReturn(new UserBuilder().with(o -> {
            o.id = VALID_ID;
            o.email = VALID_EMAIL;
            o.password = VALID_PASSWORD;
        }).buildCommand());

        final LoginResponse login = tested.login(VALID_EMAIL, VALID_PASSWORD);

        assertThat(login).isNotNull();
        assertThat(login.getEmail()).isEqualTo(VALID_EMAIL);
        assertThat(login.getId()).isEqualTo(VALID_ID);
        assertThat(login.getToken()).isNotNull();
    }

    @Test
    void throw_InvalidCredentialsException_when_login_with_invalid_credentials() throws ApiException {
        when(userService.getUserByCredentials(anyString(), anyString())).thenThrow(InvalidCredentialsException.class);

        assertThatExceptionOfType(InvalidCredentialsException.class)
                .isThrownBy(() ->
                        tested.login("invalid_email", "valid_password"));
    }

    @Test
    void register() throws ApiException {

        RegisterRequest request = new RegisterRequest();
        request.setEmail(VALID_EMAIL);
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setUsername("username");

        when(userRepository.insert(any(UserDocument.class))).thenReturn(VALID_USER);

        final RegisterResponse actual = tested.register(request);

        assertThat(actual).isEqualToComparingFieldByField(VALID_USER);
    }

    @Test
    void find_by_token() {
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(VALID_USER));

        final User userCommand = tested.findByToken(VALID_TOKEN).orElseThrow();

        assertThat(userCommand.getId()).isEqualTo(VALID_ID);

    }

    @Test
    void return_empty_user_when_invalid_token_is_given() {
        Optional<User> userCommand = tested.findByToken("invalid_token");

        assertThat(userCommand).isEmpty();
    }

    @Test
    void return_null_when_load_user_by_username() {
        final UserDetails any = tested.loadUserByUsername("any");

        assertThat(any).isNull();
    }

}
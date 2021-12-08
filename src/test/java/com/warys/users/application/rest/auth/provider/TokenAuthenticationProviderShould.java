package com.warys.users.application.rest.auth.provider;

import com.warys.users.domain.model.builder.UserBuilder;
import com.warys.users.domain.model.user.User;
import com.warys.users.domain.service.user.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.*;

class TokenAuthenticationProviderShould {

    private static final String VALID_TOKEN = "valid_token";
    private static final String VALID_USER_NAME = "userName";
    private static final String VALID_USER_ID = "userId";

    private static AuthenticationService auth = mock(AuthenticationService.class);
    private static UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);
    private TokenAuthenticationProvider tested = new TokenAuthenticationProvider(auth);

    @Test
    void do_nothing_when_additionalAuthenticationChecks_is_called() {

        final User userDetails = new UserBuilder().with(o -> {
            o.id = VALID_USER_ID;
            o.email = "email@gooo.com";
            o.username = VALID_USER_NAME;
        }).buildCommand();

        assertThatCode(() -> tested.additionalAuthenticationChecks(userDetails, authentication)).doesNotThrowAnyException();
    }

    @Test
    void retrieve_user_when_valid_authentication_credentials_are_set() {
        final User userCommand = new UserBuilder()
                .with(
                        o -> {
                            o.id = VALID_USER_ID;
                            o.email = "email@gooo.com";
                            o.username = VALID_USER_NAME;
                        }

                ).buildCommand();

        when(auth.findByToken(VALID_TOKEN)).thenReturn(Optional.of(userCommand));
        when(authentication.getCredentials()).thenReturn(VALID_TOKEN);

        final UserDetails userDetails = tested.retrieveUser(VALID_USER_NAME, authentication);

        assertThat(userDetails).isEqualToIgnoringNullFields(userCommand);
    }


    @Test
    void not_throw_UsernameNotFoundException_when_authentication_credentials_are_not_set() {
        when(authentication.getCredentials()).thenReturn(null);
        verify(auth, never()).findByToken(any());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> tested.retrieveUser(VALID_USER_NAME, authentication));
    }

    @Test
    void not_throw_UsernameNotFoundException_when_could_not_find_user_by_token() {
        when(authentication.getCredentials()).thenReturn(VALID_TOKEN);
        when(auth.findByToken(VALID_TOKEN)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> tested.retrieveUser(VALID_USER_NAME, authentication));
    }

}
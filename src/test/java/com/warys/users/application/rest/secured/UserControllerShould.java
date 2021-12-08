package com.warys.users.application.rest.secured;

import com.warys.users.application.command.request.UpdatePassword;
import com.warys.users.domain.model.builder.UserBuilder;
import com.warys.users.domain.model.user.User;
import com.warys.users.infrastructure.repository.mongo.entity.UserDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserControllerShould extends SecuredTest {

    private static final String RESOURCE = "/me";

    @BeforeEach
    void setUp() {
        init();
        UserDocument user = new UserBuilder().with(o -> {
            o.id = USER_ID;
            o.email = EMAIL;
            o.password = PASSWORD;
            o.username = USERNAME;
        }).build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findByEmailAndPassword(EMAIL, PASSWORD)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        when(userRepository.insert(any(UserDocument.class))).thenReturn(user);
        when(userRepository.save(any(UserDocument.class))).thenReturn(user);
    }

    @Test
    void get_me() throws Exception {
        this.mockMvc.perform(
                get(RESOURCE)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void return_Unauthorized_when_invalid_token_is_given() throws Exception {
        this.mockMvc.perform(
                get(RESOURCE)
                        .header("Authorization", "Bearer " + "BAD" + VALID_TOKEN + "SOO BAD")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    void update_me() throws Exception {
        User request = new UserBuilder().with(o -> {
            o.id = USER_ID;
            o.email = EMAIL;
            o.password = PASSWORD;
            o.username = "newname";
        }).buildCommand();

        this.mockMvc.perform(
                put(RESOURCE)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .content(om.writeValueAsString(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("newname")));
    }

    @Test
    void partial_update_me() throws Exception {
        User request = new UserBuilder().with(o -> o.username = "newnewname").buildCommand();

        this.mockMvc.perform(
                patch(RESOURCE)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .content(om.writeValueAsString(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(USER_ID)))
                .andExpect(jsonPath("$.username", is("newnewname")))
                .andExpect(jsonPath("$.email", is(EMAIL)));
    }

    @Test
    void update_password() throws Exception {
        UpdatePassword request = new UpdatePassword("newpassword@gmail.com");

        this.mockMvc.perform(
                put(RESOURCE + "/password")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .content(om.writeValueAsString(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void not_update_password_when_invalid_email() throws Exception {
        UpdatePassword request = new UpdatePassword("email");

        this.mockMvc.perform(
                put(RESOURCE + "/password")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .content(om.writeValueAsString(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.path", is("uri=/me/password")));
    }

    @Test
    void not_update_password_when_invalid_password() throws Exception {
        UpdatePassword request = new UpdatePassword("abc");

        this.mockMvc.perform(
                put(RESOURCE + "/password")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .content(om.writeValueAsString(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
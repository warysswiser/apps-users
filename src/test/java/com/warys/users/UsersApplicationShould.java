package com.warys.users;

import com.warys.users.application.rest.PublicUsersController;
import com.warys.users.application.rest.secured.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsersApplicationShould {

    @Autowired
    private PublicUsersController publicUsersController;
    @Autowired
    private UserController userController;

    @Test
    void load_context() {
        assertThat(publicUsersController).isNotNull();
        assertThat(userController).isNotNull();
    }

    @Test
    void launch_main() {
        UsersApplication.main(new String[]{});
    }

}

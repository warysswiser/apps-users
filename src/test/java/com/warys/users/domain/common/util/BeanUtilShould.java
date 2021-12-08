package com.warys.users.domain.common.util;

import com.warys.users.domain.model.builder.UserBuilder;
import com.warys.users.domain.model.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BeanUtilShould {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void make_a_complete_copy_of_origin_user_when_destination_is_empty() {

        var orig = new UserBuilder()
                .with(
                        o -> {
                            o.id = "userId";
                            o.firstName = "First";
                            o.lastName = "Last";
                            o.email = "email@go.com";
                            o.password = "12345678";
                            o.creationDate = NOW.minusMonths(1);
                            o.updateDate = NOW.plusWeeks(2);
                            o.deletionDate = NOW.plusWeeks(3);
                        })
                .build();

        var dest = new User();

        BeanUtil.copyBean(orig, dest);

        assertThat(dest).doesNotHaveSameClassAs(orig);
        assertThat(dest).isEqualToComparingFieldByField(orig);
    }

}
package com.warys.users.domain.common.mapper;

import com.warys.users.domain.common.util.BeanUtil;

import java.util.function.Function;

public interface BeanMapper<I, O> {

    Function<O, I> mapToOutput();

    Function<I, O> mapToInput();

    default void map(Object orig, Object dest) {
        BeanUtil.copyBean(orig, dest);
    }
}

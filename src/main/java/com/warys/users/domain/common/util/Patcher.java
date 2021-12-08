package com.warys.users.domain.common.util;

public class Patcher {

    private Patcher() {
    }

    public static void patch(Object orig, Object dest) {
        BeanUtil.copyBean(orig, dest);
    }
}

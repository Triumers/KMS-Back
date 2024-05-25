package org.triumers.kmsback.common;

import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;

public class TestUserInfo {
    public static final String EMAIL = "testUnique@gmail.com";
    public static final String PASSWORD = "aAbB1234";
    public static final String NAME = "테스트유저";
    public static final String USER_ROLE = UserRole.ROLE_NORMAL.name();
    public static final String PHONE_NUMBER = "010-1234-5678";

    public static final String ADMIN_EMAIL = "adminUnique@gmail.com";
    public static final String HR_MANAGER_ROLE = UserRole.ROLE_HR_MANAGER.name();
}

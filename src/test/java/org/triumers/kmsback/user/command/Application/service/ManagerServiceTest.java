package org.triumers.kmsback.user.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManagerServiceTest {

    @Value("${password}")
    private String DEFAULT_PASSWORD;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LoggedInUser loggedInUser;

    @DisplayName("회원가입 테스트")
    @Test
    void signupTest() {

        // given
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.USER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);

        // when
        assertDoesNotThrow(() -> managerService.signup(userDTO));

        // then
        assertNotNull(employeeRepository.findByEmail(TestUserInfo.EMAIL));
    }

    @DisplayName("기본 비밀번호로 회원가입 테스트")
    @Test
    void signupWithDefaultPasswordTest() {

        // given
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.EMAIL, null, TestUserInfo.NAME, null,
                TestUserInfo.USER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);

        // when
        assertDoesNotThrow(() -> managerService.signup(userDTO));

        // then
        assertTrue(bCryptPasswordEncoder.matches(DEFAULT_PASSWORD, employeeRepository.findByEmail(TestUserInfo.EMAIL).getPassword()));
    }

    @DisplayName("회원 권한 수정 테스트")
    @Test
    void editUserRoleTest() {

        // given
        addUserForTest();
        ManageUserDTO targetUser = new ManageUserDTO();
        targetUser.setEmail(TestUserInfo.EMAIL);
        targetUser.setRole(UserRole.ROLE_LEADER.name());

        // when
        loggedInUser.settingHrManager();
        assertDoesNotThrow(() -> managerService.editUserRole(targetUser));

        // then
        assertEquals(targetUser.getRole(), UserRole.ROLE_LEADER.name());
        assertNotEquals(targetUser.getRole(), TestUserInfo.USER_ROLE);
    }

    @DisplayName("자신의 권한을 초과하는 권한 부여 예외 테스트")
    @Test
    void editOverRoleExceptionTest() {

        // given
        addUserForTest();
        ManageUserDTO targetUser = new ManageUserDTO();
        targetUser.setEmail(TestUserInfo.EMAIL);
        targetUser.setRole(UserRole.ROLE_ADMIN.name());

        // when
        loggedInUser.settingHrManager();

        // then
        assertThrows(NotAuthorizedException.class, () -> managerService.editUserRole(targetUser));
    }

    @DisplayName("자신의 권한을 초과하는 유저 권한 수정 예외 테스트")
    @Test
    void editOverRoleUSerExceptionTest() {

        // given
        addHrManagerForTest();
        ManageUserDTO targetUser = new ManageUserDTO();
        targetUser.setEmail(TestUserInfo.HR_MANAGER_EMAIL);
        targetUser.setRole(UserRole.ROLE_NORMAL.name());

        // when
        loggedInUser.setting();

        // then
        assertThrows(NotAuthorizedException.class, () -> managerService.editUserRole(targetUser));
    }

    private void addUserForTest() {
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.USER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);
        managerService.signup(userDTO);
    }

    private void addHrManagerForTest() {
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.HR_MANAGER_EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.HR_MANAGER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);
        managerService.signup(userDTO);
    }
}
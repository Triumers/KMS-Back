package org.triumers.kmsback.user.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ManagerServiceImplTest {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("회원가입 테스트")
    @Test
    void signup() {

        // given
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.USER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);

        // when
        assertDoesNotThrow(() -> managerService.signup(userDTO));

        // then
        assertNotNull(employeeRepository.findByEmail(TestUserInfo.EMAIL));
    }
}
package org.triumers.kmsback.user.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.user.command.Application.dto.AuthDTO;
import org.triumers.kmsback.user.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeServiceTest {
    private final String RIGHT_FORMAT_EMAIL = TestUserInfo.EMAIL;
    private final String RIGHT_FORMAT_PASSWORD = TestUserInfo.PASSWORD;
    private final String RIGHT_FORMAT_NAME = TestUserInfo.NAME;
    private final String RIGHT_FORMAT_USER_ROLE = TestUserInfo.USER_ROLE;
    private final String RIGHT_PHONE_NUMBER = TestUserInfo.PHONE_NUMBER;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LoggedInUser loggedInUser;

    @DisplayName("회원가입 테스트")
    @Test
    void signup() {

        // given
        AuthDTO newEmployee = createRightAuthDTO();

        // when
        assertDoesNotThrow(() -> authService.signup(newEmployee));

        // then
        assertNotNull(employeeRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("비밀번호 변경 테스트")
    @Test
    void editPassword() throws WrongInputTypeException {

        // given
        loggedInUser.setting();
        String newPassword = "NewPwd1234";
        PasswordDTO passwordDTO = new PasswordDTO(RIGHT_FORMAT_PASSWORD, newPassword);

        // when
        assertDoesNotThrow(() -> authService.editPassword(passwordDTO));

        // then
        assertTrue(bCryptPasswordEncoder.matches(newPassword, employeeRepository.findByEmail(RIGHT_FORMAT_EMAIL).getPassword()));
    }

    @DisplayName("잘못된 기존 비밀번호 변경 테스트")
    @Test
    void wrongOldPasswordEditPassword() throws WrongInputTypeException {

        // given
        loggedInUser.setting();
        String newPassword = "NewPwd1234";
        PasswordDTO passwordDTO = new PasswordDTO("wrongBefore1", newPassword);

        // when
        assertThrows(WrongInputValueException.class, () -> authService.editPassword(passwordDTO));

        // then
        assertFalse(bCryptPasswordEncoder.matches(newPassword, employeeRepository.findByEmail(RIGHT_FORMAT_EMAIL).getPassword()));
    }

    @DisplayName("사용자 정보 변경 테스트")
    @ParameterizedTest
    @CsvSource({
            "NewName, 010-7777-7777, testImg.jpg",
            ", 010-7777-7777, testImg.jpg",
            "NewName, , testImg.jpg",
            "NewName, 010-7777-7777, ",
            ", , testImg.jpg",
            "NewName, , ",
            ", 010-7777-7777, "
    })
    void editMyInfo(String newName, String newPhoneNumber, String newProfileImg) throws WrongInputTypeException, NotLoginException {

        // given
        loggedInUser.setting();

        AuthDTO authDTO = new AuthDTO();
        authDTO.setName(newName);
        authDTO.setPhoneNumber(newPhoneNumber);
        authDTO.setProfileImg(newProfileImg);

        // when
        authService.editMyInfo(authDTO);

        // then
        Employee employee = employeeRepository.findByEmail(RIGHT_FORMAT_EMAIL);

        if (newName != null && !newName.isEmpty()) {
            assertEquals(newName, employee.getName());
        }
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            assertEquals(newPhoneNumber, employee.getPhoneNumber());
        }
        if (newProfileImg != null && !newProfileImg.isEmpty()) {
            assertEquals(newProfileImg, employee.getProfileImg());
        }
    }

    // 테스트용 계정 DTO 생성
    private AuthDTO createRightAuthDTO() {
        return new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }
}
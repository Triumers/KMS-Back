package org.triumers.kmsback.auth.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;
import org.triumers.kmsback.common.exception.WrongInputTypeException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {
    private final String RIGHT_FORMAT_EMAIL = "testUnique@gmail.com";
    private final String RIGHT_FORMAT_PASSWORD = "aAbB1234";
    private final String RIGHT_FORMAT_NAME = "테스트유저";
    private final String RIGHT_FORMAT_USER_ROLE = UserRole.ROLE_NORMAL.name();
    private final String RIGHT_PHONE_NUMBER = "010-1234-5678";

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @DisplayName("회원가입 테스트")
    @Test
    void signup() {

        // given
        AuthDTO newEmployee =
                new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                        RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                        1);

        // when
        assertDoesNotThrow(() -> authService.signup(newEmployee));

        // then
        assertNotNull(authRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("이메일 형식이 잘못된 회원가입 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"nothing", "noAnnotation.com", "noPoint@gmail",
            "containKorean@지메일.com", "containSpecialChar@gmail.com!"})
    void invalidEmailSignup(String wrongEmail) {

        // given
        AuthDTO newEmployee =
                new AuthDTO(wrongEmail, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                        RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                        1);

        // when
        assertThrows(WrongInputTypeException.class, () -> authService.signup(newEmployee));

        // then
        assertNull(authRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("비밀번호 형식이 잘못된 회원가입 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"Short1", "TooWrongPassword1234", "noNumberPwd",
            "no1upper", "NO1SMALL", "한글포함Pwd123", "Special123!"})
    void invalidPasswordSignup(String wrongPassword) {

        // given
        AuthDTO newEmployee =
                new AuthDTO(RIGHT_FORMAT_EMAIL, wrongPassword, RIGHT_FORMAT_NAME, null,
                        RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                        1);

        // when
        assertThrows(WrongInputTypeException.class, () -> authService.signup(newEmployee));

        // then
        assertNull(authRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("이름 형식이 잘못된 회원가입 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"ContainNumber123", "ContainSpecial!"})
    void invalidNameSignup(String wrongName) {

        // given
        AuthDTO newEmployee =
                new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, wrongName, null,
                        RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                        1);

        // when
        assertThrows(WrongInputTypeException.class, () -> authService.signup(newEmployee));

        // then
        assertNull(authRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("휴대폰 번호 형식이 잘못된 회원가입 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"010-alph-1234", "010-!!!!-1234", "000-1234-5678", "01-1234-5678", "0100-1234-5678",
                            "010-12-5678", "010-12345-6789", "010-1234-567", "010-1234-56789"})
    void invalidPhoneNumberSignup(String wrongNumber) {

        // given
        AuthDTO newEmployee =
                new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                        RIGHT_FORMAT_USER_ROLE, null, null, wrongNumber, 1, 1,
                        1);

        // when
        assertThrows(WrongInputTypeException.class, () -> authService.signup(newEmployee));

        // then
        assertNull(authRepository.findByEmail(newEmployee.getEmail()));
    }
}
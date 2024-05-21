package org.triumers.kmsback.auth.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;
import org.triumers.kmsback.auth.command.domain.service.CustomUserDetailsService;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    void signup() {

        // given
        AuthDTO newEmployee = createRightAuthDTO();

        // when
        assertDoesNotThrow(() -> authService.signup(newEmployee));

        // then
        assertNotNull(authRepository.findByEmail(newEmployee.getEmail()));
    }

    @DisplayName("비밀번호 변경 테스트")
    @Test
    void editPassword() throws WrongInputTypeException, WrongInputValueException {

        // given
        setSecurityContextHolderByUserName();
        String newPassword = "NewPwd1234";
        PasswordDTO passwordDTO = new PasswordDTO(RIGHT_FORMAT_PASSWORD, newPassword);

        // when
        assertDoesNotThrow(() -> authService.editPassword(passwordDTO));

        // then
        assertTrue(bCryptPasswordEncoder.matches(newPassword, authRepository.findByEmail(RIGHT_FORMAT_EMAIL).getPassword()));
    }

    @DisplayName("잘못된 기존 비밀번호 변경 테스트")
    @Test
    void wrongOldPasswordEditPassword() throws WrongInputTypeException, WrongInputValueException {

        // given
        setSecurityContextHolderByUserName();
        String newPassword = "NewPwd1234";
        PasswordDTO passwordDTO = new PasswordDTO("wrongBefore1", newPassword);

        // when
        assertThrows(WrongInputValueException.class, () -> authService.editPassword(passwordDTO));

        // then
        assertFalse(bCryptPasswordEncoder.matches(newPassword, authRepository.findByEmail(RIGHT_FORMAT_EMAIL).getPassword()));
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
        setSecurityContextHolderByUserName();

        AuthDTO authDTO = new AuthDTO();
        authDTO.setName(newName);
        authDTO.setPhoneNumber(newPhoneNumber);
        authDTO.setProfileImg(newProfileImg);

        // when
        authService.editMyInfo(authDTO);

        // then
        Auth auth = authRepository.findByEmail(RIGHT_FORMAT_EMAIL);

        if (newName != null && !newName.isEmpty()) {
            assertEquals(newName, auth.getName());
        }
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            assertEquals(newPhoneNumber, auth.getPhoneNumber());
        }
        if (newProfileImg != null && !newProfileImg.isEmpty()) {
            assertEquals(newProfileImg, auth.getProfileImg());
        }
    }

    // 테스트용 계정 DTO 생성
    private AuthDTO createRightAuthDTO() {
        return new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }

    // 테스트용 계정 회원가입
    private void setUser() throws WrongInputTypeException {
        authService.signup(createRightAuthDTO());
    }

    // 테스트용 계정으로 로그인
    private void setSecurityContextHolderByUserName() throws WrongInputTypeException {
        setUser();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(authRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(RIGHT_FORMAT_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
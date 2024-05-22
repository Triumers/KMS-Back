package org.triumers.kmsback.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.Application.service.AuthService;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;
import org.triumers.kmsback.auth.command.domain.service.CustomUserDetailsService;
import org.triumers.kmsback.common.exception.WrongInputTypeException;

@Component
public class LoggedInUser {
    private final String RIGHT_FORMAT_EMAIL = TestUserInfo.EMAIL;
    private final String RIGHT_FORMAT_PASSWORD = TestUserInfo.PASSWORD;
    private final String RIGHT_FORMAT_NAME = TestUserInfo.NAME;
    private final String RIGHT_FORMAT_USER_ROLE = TestUserInfo.USER_ROLE;
    private final String RIGHT_PHONE_NUMBER = TestUserInfo.PHONE_NUMBER;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    // 테스트용 계정 DTO 생성
    private AuthDTO createRightAuthDTO() {
        return new AuthDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }

    // 테스트용 계정 회원가입
    private void setUser() {
        authService.signup(createRightAuthDTO());
    }

    // 테스트용 계정으로 로그인
    public void setting() throws WrongInputTypeException {
        setUser();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(authRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(RIGHT_FORMAT_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}

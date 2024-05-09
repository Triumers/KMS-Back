package org.triumers.kmsback.auth.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @Test
    void signup() {

        // given
        AuthDTO newEmployee =
                new AuthDTO("test@gmail.com", null, "테스트유저", null, "NORMAL", null, null, "010-1234-5678", 1, 1, 1);

        // when, then
        assertDoesNotThrow(() -> authService.signup(newEmployee));
    }
}
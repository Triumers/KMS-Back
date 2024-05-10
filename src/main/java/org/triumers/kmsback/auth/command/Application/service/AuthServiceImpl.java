package org.triumers.kmsback.auth.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService {// Git 충돌 복구용 주석
    private final String DEFAULT_PASSWORD;

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(@Value("${password}") String password, AuthRepository authRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.DEFAULT_PASSWORD = password;
        this.authRepository = authRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signup(AuthDTO authDTO) {

        authRepository.save(authMapper(authDTO));
    }

    private Auth authMapper(AuthDTO authDTO) {
        Auth auth = new Auth();

        auth.setEmail(authDTO.getEmail());
        auth.setName(authDTO.getName());
        auth.setProfileImg(authDTO.getProfileImg());
        auth.setUserRole(UserRole.valueOf(authDTO.getRole()));
        auth.setStartDate(authDTO.getStartDate());
        auth.setEndDate(authDTO.getEndDate());
        auth.setPhoneNumber(authDTO.getPhoneNumber());
        auth.setTeamId(authDTO.getTeamId());
        auth.setPositionId(authDTO.getPositionId());
        auth.setRankId(authDTO.getRankId());

        if (authDTO.getPassword() != null) {
            auth.setPassword(bCryptPasswordEncoder.encode(authDTO.getPassword()));
            return auth;
        }
        auth.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));

        return auth;
    }
}

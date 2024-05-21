package org.triumers.kmsback.auth.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {
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

    @Override
    public void editPassword(PasswordDTO passwordDTO) throws WrongInputValueException, NotLoginException {

        Auth auth = whoAmI();

        if (bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), auth.getPassword())) {

            auth.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));

            authRepository.save(auth);

            return;
        }
        throw new WrongInputValueException();
    }

    @Override
    public void editMyInfo(AuthDTO authDTO) throws NotLoginException {

        Auth auth = whoAmI();

        if (authDTO.getName() != null) {
            auth.setName(authDTO.getName());
        }
        if (authDTO.getPhoneNumber() != null) {
            auth.setPhoneNumber(authDTO.getPhoneNumber());
        }
        if (authDTO.getProfileImg() != null) {
            auth.setProfileImg(authDTO.getProfileImg());
        }

        authRepository.save(auth);
    }

    // 현재 로그인된 계정 정보 조회
    @Override
    public Auth whoAmI() throws NotLoginException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Auth auth = authRepository.findByEmail(email);

        if (auth == null) {
            throw new NotLoginException();
        }

        return auth;
    }

    // AuthDTO to Auth changer
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

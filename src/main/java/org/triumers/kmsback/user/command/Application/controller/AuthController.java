package org.triumers.kmsback.user.command.Application.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.user.command.Application.dto.AuthDTO;
import org.triumers.kmsback.user.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdRequestEditMyInfoVO;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdRequestEditPasswordVO;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdResponseMessageVO;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

@RestController
@Validated
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/edit/password")
    public ResponseEntity<CmdResponseMessageVO> editPassword(@Valid @RequestBody CmdRequestEditPasswordVO request) {
        PasswordDTO passwordDTO = new PasswordDTO(request.getOldPassword(), request.getNewPassword());

        try {
            authService.editPassword(passwordDTO);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO("비밀번호 변경 성공"));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CmdResponseMessageVO("로그인 이후 이용해주세요."));
        } catch (WrongInputTypeException | WrongInputValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CmdResponseMessageVO("잘못된 입력입니다."));
        }
    }

    @PostMapping("/edit/my-info")
    public ResponseEntity<CmdResponseMessageVO> editMyInfo(@Valid @RequestBody CmdRequestEditMyInfoVO request) {

        AuthDTO authDTO = new AuthDTO();
        if (request.getName()!= null && !request.getName().isEmpty()) {
            authDTO.setName(request.getName());
        }
        if (request.getPhoneNumber()!= null && !request.getPhoneNumber().isEmpty()) {
            authDTO.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getProfileImg()!= null && !request.getProfileImg().isEmpty()) {
            authDTO.setProfileImg(request.getProfileImg());
        }

        try {
            authService.editMyInfo(authDTO);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO("변경 성공"));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CmdResponseMessageVO("로그인 이후 이용해주세요."));
        } catch (WrongInputTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CmdResponseMessageVO("잘못된 입력입니다."));
        }
    }
}

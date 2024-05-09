package org.triumers.kmsback.auth.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.Application.service.AuthService;
import org.triumers.kmsback.auth.command.domain.aggregate.vo.CmdRequestAuthVO;
import org.triumers.kmsback.auth.command.domain.aggregate.vo.CmdResponseMessageVO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CmdResponseMessageVO> signup(@RequestBody CmdRequestAuthVO request) {
        AuthDTO authDTO = authDtoMapper(request);
        try {
            authService.signup(authDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CmdResponseMessageVO(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO(authDTO.getName() + " 회원가입 성공"));
    }

    private AuthDTO authDtoMapper(CmdRequestAuthVO request) {
        AuthDTO auth = new AuthDTO();

        auth.setEmail(request.getEmail());
        auth.setPassword(request.getPassword());
        auth.setName(request.getName());
        auth.setProfileImg(request.getProfileImg());
        auth.setRole(request.getRole());
        auth.setStartDate(request.getStartDate());
        auth.setEndDate(request.getEndDate());
        auth.setPhoneNumber(request.getPhoneNumber());
        auth.setTeamId(request.getTeamId());
        auth.setPositionId(request.getPositionId());
        auth.setRankId(request.getRankId());

        return auth;
    }
}

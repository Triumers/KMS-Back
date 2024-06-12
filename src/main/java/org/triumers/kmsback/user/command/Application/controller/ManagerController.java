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
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdRequestEditUserVO;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdRequestSignupUserVO;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdResponseMessageVO;

@RestController
@Validated
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CmdResponseMessageVO> signup(@Valid @RequestBody CmdRequestSignupUserVO request) {

        try {
            ManageUserDTO newEmployee = new ManageUserDTO();

            newEmployee.setEmail(request.getEmail());
            newEmployee.setPassword(request.getPassword());
            newEmployee.setName(request.getName());
            newEmployee.setProfileImg(request.getProfileImg());
            newEmployee.setRole(request.getRole());
            newEmployee.setStartDate(request.getStartDate());
            newEmployee.setEndDate(request.getEndDate());
            newEmployee.setPhoneNumber(request.getPhoneNumber());
            newEmployee.setTeamId(request.getTeamId());
            newEmployee.setPositionId(request.getPositionId());
            newEmployee.setRankId(request.getRankId());

            managerService.signup(newEmployee);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO(newEmployee.getName() + " 회원 추가 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/edit/role")
    public ResponseEntity<CmdResponseMessageVO> editUserRole(@Valid @RequestBody CmdRequestEditUserVO request) {

        ManageUserDTO targetEmployee = new ManageUserDTO();

        targetEmployee.setEmail(request.getEmail());
        targetEmployee.setRole(request.getRole());

        try {
            managerService.editUserRole(targetEmployee);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO("권한 수정 성공"));
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new CmdResponseMessageVO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/edit/password")
    public ResponseEntity<CmdResponseMessageVO> initializePassword(@Valid @RequestBody CmdRequestEditUserVO request) {
        ManageUserDTO targetEmployee = new ManageUserDTO();

        targetEmployee.setEmail(request.getEmail());
        targetEmployee.setPassword(request.getPassword());

        try {
            managerService.initializePassword(targetEmployee);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO("비밀번호 초기화 성공"));
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new CmdResponseMessageVO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/edit/info")
    public ResponseEntity<CmdResponseMessageVO> editInfo(@Valid @RequestBody CmdRequestEditUserVO request) {
        ManageUserDTO targetEmployee = new ManageUserDTO();

        targetEmployee.setEmail(request.getEmail());
        targetEmployee.setName(request.getName());
        targetEmployee.setProfileImg(request.getProfileImg());
        targetEmployee.setStartDate(request.getStartDate());
        targetEmployee.setEndDate(request.getEndDate());
        targetEmployee.setPhoneNumber(request.getPhoneNumber());
        targetEmployee.setTeamId(request.getTeamId());
        targetEmployee.setPositionId(request.getPositionId());
        targetEmployee.setRankId(request.getRankId());

        try {
            managerService.editUserInfo(targetEmployee);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CmdResponseMessageVO("정보 수정 성공"));
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new CmdResponseMessageVO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

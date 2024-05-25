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
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
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
    }
}

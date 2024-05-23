package org.triumers.kmsback.approval.command.Application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.Application.service.CmdApprovalTypeService;
import org.triumers.kmsback.approval.command.Application.service.CmdRequestApprovalService;
import org.triumers.kmsback.auth.command.Application.service.AuthService;
import org.triumers.kmsback.common.exception.NotLoginException;

@RestController
@RequestMapping("/approval")
public class CmdRequestApprovalController {

    private final CmdRequestApprovalService cmdRequestApprovalService;
    private final AuthService authService; // AuthService 주입
    private final CmdApprovalTypeService cmdApprovalTypeService;

    public CmdRequestApprovalController(CmdRequestApprovalService cmdRequestApprovalService, AuthService authService, CmdApprovalTypeService cmdApprovalTypeService) {
        this.cmdRequestApprovalService = cmdRequestApprovalService;
        this.authService = authService;
        this.cmdApprovalTypeService = cmdApprovalTypeService;
    }

    // 현재 사용자의 ID를 얻는 헬퍼 메서드
    private int getCurrentUserId() throws NotLoginException {
        return authService.whoAmI().getId();
    }

    @PostMapping
    public ResponseEntity<Void> createApproval(@RequestBody CmdApprovalRequestDTO requestDto) {
        try {
            int requesterId = getCurrentUserId(); // 헬퍼 메서드 사용
            cmdRequestApprovalService.createApproval(requestDto, requesterId);
            return ResponseEntity.ok().build();
        } catch (NotLoginException e) {
            return ResponseEntity.status(401).build(); // Unauthorized 응답
        }
    }

    @PostMapping("/approval-type")
    public ResponseEntity<String> addNewApprovalType(@RequestBody String type) {
        cmdApprovalTypeService.addNewApprovalType(type);
        return new ResponseEntity<>("New approval type added successfully", HttpStatus.OK);
    }

    @PostMapping("/{approvalId}/cancel")
    public ResponseEntity<String> cancelApproval(@PathVariable int approvalId) {
        try {
            int requesterId = getCurrentUserId();
            cmdRequestApprovalService.cancelApproval(requesterId, approvalId);
            return ResponseEntity.ok("Approval canceled successfully");
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/approve")
    public ResponseEntity<String> approveRequestApproval(@PathVariable int requestApprovalId) {
        try {
            int approverId = authService.whoAmI().getId();
            cmdRequestApprovalService.approveRequestApproval(approverId, requestApprovalId);
            return ResponseEntity.ok("Request approval approved successfully");
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/reject")
    public ResponseEntity<String> rejectRequestApproval(@PathVariable int requestApprovalId) {
        try {
            int approverId = authService.whoAmI().getId();
            cmdRequestApprovalService.rejectRequestApproval(approverId, requestApprovalId);
            return ResponseEntity.ok("Request approval rejected successfully");
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/addApprover")
    public ResponseEntity<String> addApproverToRequestApproval(@PathVariable int requestApprovalId, @RequestParam int newApproverId) {
        try {
            int approverId = authService.whoAmI().getId();
            cmdRequestApprovalService.addApproverToRequestApproval(approverId, requestApprovalId, newApproverId);
            return ResponseEntity.ok("Approver added successfully");
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

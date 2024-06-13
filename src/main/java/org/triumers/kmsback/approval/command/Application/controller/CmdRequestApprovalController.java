package org.triumers.kmsback.approval.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.Application.service.CmdApprovalTypeService;
import org.triumers.kmsback.approval.command.Application.service.CmdRequestApprovalService;
import org.triumers.kmsback.common.exception.NotLoginException;

import java.util.Map;

@RestController
@RequestMapping("/approval")
public class CmdRequestApprovalController {

    private final CmdRequestApprovalService cmdRequestApprovalService;
    private final CmdApprovalTypeService cmdApprovalTypeService;

    @Autowired
    public CmdRequestApprovalController(CmdRequestApprovalService cmdRequestApprovalService, CmdApprovalTypeService cmdApprovalTypeService) {
        this.cmdRequestApprovalService = cmdRequestApprovalService;
        this.cmdApprovalTypeService = cmdApprovalTypeService;
    }

    @PostMapping
    public ResponseEntity<Void> createApproval(@RequestBody CmdApprovalRequestDTO requestDto) throws NotLoginException {

        try {
            cmdRequestApprovalService.createApproval(requestDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/approval-type")
    public ResponseEntity<String> addNewApprovalType(@RequestBody String type) {

        try {
            cmdApprovalTypeService.addNewApprovalType(type);
            return new ResponseEntity<>("New approval type added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{approvalId}/cancel")
    public ResponseEntity<String> cancelApproval(@PathVariable int approvalId) {
        try {
            cmdRequestApprovalService.cancelApproval(approvalId);
            return ResponseEntity.ok("Approval canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/approve")
    public ResponseEntity<String> approveRequestApproval(@PathVariable int requestApprovalId) {
        try {
            cmdRequestApprovalService.approveRequestApproval(requestApprovalId);
            return ResponseEntity.ok("Request approval approved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/reject")
    public ResponseEntity<String> rejectRequestApproval(@PathVariable int requestApprovalId) {
        try {
            cmdRequestApprovalService.rejectRequestApproval(requestApprovalId);
            return ResponseEntity.ok("Request approval rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received/{requestApprovalId}/addApprover")
    public ResponseEntity<String> addApproverToRequestApproval(@PathVariable int requestApprovalId, @RequestBody Map<String, Integer> requestBody) {
        try {
            int newApproverId = requestBody.get("newApproverId");
            cmdRequestApprovalService.addApproverToRequestApproval(requestApprovalId, newApproverId);
            return ResponseEntity.ok("Approver added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
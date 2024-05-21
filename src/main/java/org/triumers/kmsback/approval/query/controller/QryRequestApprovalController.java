package org.triumers.kmsback.approval.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.service.QryRequestApprovalService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/approval")
public class QryRequestApprovalController {

    private final QryRequestApprovalService qryRequestApprovalService;

    @Autowired
    public QryRequestApprovalController(QryRequestApprovalService qryRequestApprovalService) {
        this.qryRequestApprovalService = qryRequestApprovalService;
    }

    // 본인이 요청한 결재 단일 조회
    // requesterId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 4로 통일
    @GetMapping("/{approvalId}")
    public QryRequestApprovalWithEmployeeDTO findById(@RequestParam(value = "requesterId", defaultValue = "4") int requesterId,
                                                      @PathVariable("approvalId") int approvalId) {
        return qryRequestApprovalService.findById(requesterId, approvalId);
    }

    // 본인이 요청한 결재 전체 조회(페이징 처리)
    // requesterId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 4로 통일
    @GetMapping
    public List<QryRequestApprovalInfoDTO> findAll(@RequestParam(value = "requesterId", defaultValue = "4") int requesterId,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findAll(requesterId, page, size);
    }

    // 본인이 요청한 결재 유형별 조회(페이징 처리)
    // requesterId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 4로 통일
    @GetMapping("/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findByType(@RequestParam(value = "requesterId", defaultValue = "4") int requesterId,
                                                      @PathVariable("typeId") int typeId,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findByType(requesterId, typeId, page, size);
    }

    // 본인이 요청한 결재 기간별 조회(페이징 처리)
    // requesterId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 4로 통일
    @GetMapping("/date-range")
    public List<QryRequestApprovalInfoDTO> findByDateRange(@RequestParam(value = "requesterId", defaultValue = "4") int requesterId,
                                                           @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findByDateRange(requesterId, startDate, endDate, page, size);
    }


}

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


    // 본인이 요청받은 결재 단일 조회
    // approverId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 2로 통일
    @GetMapping("/received/{requestApprovalId}")
    public QryRequestApprovalWithEmployeeDTO findReceivedById(@RequestParam(value = "approverId", defaultValue = "2") int approverId,
                                                      @PathVariable("requestApprovalId") int requestApprovalId) {
        return qryRequestApprovalService.findReceivedById(approverId, requestApprovalId);
    }

    // 본인이 요청받은 결재 전체 조회(페이징 처리)
    // approverId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 2로 통일
    @GetMapping("/received")
    public List<QryRequestApprovalInfoDTO> findAllReceived(@RequestParam(value = "approverId", defaultValue = "2") int approverId,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findAllReceived(approverId, page, size);
    }

    // 본인이 요청받은 결재 유형별 조회(페이징 처리)
    // approverId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 2로 통일
    @GetMapping("/received/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findReceivedByType(@RequestParam(value = "approverId", defaultValue = "2") int approverId,
                                                      @PathVariable("typeId") int typeId,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findReceivedByType(approverId, typeId, page, size);
    }

    // 본인이 요청받은 결재 기간별 조회(페이징 처리)
    // approverId = 추후에 받아오는 아이디로 수정, 우선 테스트용이니 2로 통일
    @GetMapping("/received/date-range")
    public List<QryRequestApprovalInfoDTO> findReceivedByDateRange(@RequestParam(value = "approverId", defaultValue = "2") int approverId,
                                                           @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return qryRequestApprovalService.findReceivedByDateRange(approverId, startDate, endDate, page, size);
    }
}

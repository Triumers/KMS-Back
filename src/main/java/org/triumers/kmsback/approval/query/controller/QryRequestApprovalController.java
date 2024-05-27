package org.triumers.kmsback.approval.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.service.QryRequestApprovalService;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.service.AuthService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/approval")
public class QryRequestApprovalController {

    private final QryRequestApprovalService qryRequestApprovalService;
    private final AuthService authService;

    @Autowired
    public QryRequestApprovalController(QryRequestApprovalService qryRequestApprovalService, AuthService authService) {
        this.qryRequestApprovalService = qryRequestApprovalService;
        this.authService = authService;
    }

    // 현재 사용자의 ID를 얻는 헬퍼 메서드
    private int getCurrentUserId() throws NotLoginException {
        return authService.whoAmI().getId();
    }

    // 본인이 요청한 결재 단일 조회
    @GetMapping("/{approvalId}")
    public QryRequestApprovalWithEmployeeDTO findById(@PathVariable("approvalId") int approvalId) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findById(requesterId, approvalId);
    }

    // 본인이 요청한 결재 전체 조회(페이징 처리)
    @GetMapping
    public List<QryRequestApprovalInfoDTO> findAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findAll(requesterId, page, size);
    }

    // 본인이 요청한 결재 유형별 조회(페이징 처리)
    @GetMapping("/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findByType(@PathVariable("typeId") int typeId,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findByType(requesterId, typeId, page, size);
    }

    // 본인이 요청한 결재 기간별 조회(페이징 처리)
    @GetMapping("/date-range")
    public List<QryRequestApprovalInfoDTO> findByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findByDateRange(requesterId, startDate, endDate, page, size);
    }

    // 본인이 요청받은 결재 단일 조회
    @GetMapping("/received/{requestApprovalId}")
    public QryRequestApprovalWithEmployeeDTO findReceivedById(@PathVariable("requestApprovalId") int requestApprovalId) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findReceivedById(approverId, requestApprovalId);
    }

    // 본인이 요청받은 결재 전체 조회(페이징 처리)
    @GetMapping("/received")
    public List<QryRequestApprovalInfoDTO> findAllReceived(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findAllReceived(approverId, page, size);
    }

    // 본인이 요청받은 결재 유형별 조회(페이징 처리)
    @GetMapping("/received/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findReceivedByType(@PathVariable("typeId") int typeId,
                                                              @RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findReceivedByType(approverId, typeId, page, size);
    }

    // 본인이 요청받은 결재 기간별 조회(페이징 처리)
    @GetMapping("/received/date-range")
    public List<QryRequestApprovalInfoDTO> findReceivedByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findReceivedByDateRange(approverId, startDate, endDate, page, size);
    }

    // 본인이 요청한 결재 내용 검색
    @GetMapping("/search")
    public List<QryRequestApprovalInfoDTO> findByContent(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findByContent(requesterId, keyword, page, size);
    }

    // 본인이 요청한 결재 승인 상태별 조회
    @GetMapping("/status/{status}")
    public List<QryRequestApprovalInfoDTO> findByStatus(
            @PathVariable("status") String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        int requesterId = getCurrentUserId();
        return qryRequestApprovalService.findByStatus(requesterId, status, page, size);
    }

    // 본인이 요청받은 결재 내용 검색
    @GetMapping("/received/search")
    public List<QryRequestApprovalInfoDTO> findReceivedByContent(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findReceivedByContent(approverId, keyword, page, size);
    }

    // 본인이 요청받은 결재 승인 상태별 조회
    @GetMapping("/received/status/{status}")
    public List<QryRequestApprovalInfoDTO> findReceivedByStatus(
            @PathVariable("status") String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        int approverId = getCurrentUserId();
        return qryRequestApprovalService.findReceivedByStatus(approverId, status, page, size);
    }

}

@RestControllerAdvice
class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNotLoginException(NotLoginException ex) {
        return "User is not logged in.";
    }
}

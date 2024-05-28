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
    @GetMapping("/{approvalId}")
    public QryRequestApprovalWithEmployeeDTO findById(@PathVariable("approvalId") int approvalId) throws NotLoginException {
        return qryRequestApprovalService.findById(approvalId);
    }

    // 본인이 요청한 결재 전체 조회(페이징 처리)
    @GetMapping
    public List<QryRequestApprovalInfoDTO> findAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findAll(page, size);
    }

    // 본인이 요청한 결재 유형별 조회(페이징 처리)
    @GetMapping("/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findByType(@PathVariable("typeId") int typeId,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findByType(typeId, page, size);
    }

    // 본인이 요청한 결재 기간별 조회(페이징 처리)
    @GetMapping("/date-range")
    public List<QryRequestApprovalInfoDTO> findByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findByDateRange(startDate, endDate, page, size);
    }

    // 본인이 요청받은 결재 단일 조회
    @GetMapping("/received/{requestApprovalId}")
    public QryRequestApprovalWithEmployeeDTO findReceivedById(@PathVariable("requestApprovalId") int requestApprovalId) throws NotLoginException {
        return qryRequestApprovalService.findReceivedById(requestApprovalId);
    }

    // 본인이 요청받은 결재 전체 조회(페이징 처리)
    @GetMapping("/received")
    public List<QryRequestApprovalInfoDTO> findAllReceived(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findAllReceived(page, size);
    }

    // 본인이 요청받은 결재 유형별 조회(페이징 처리)
    @GetMapping("/received/type/{typeId}")
    public List<QryRequestApprovalInfoDTO> findReceivedByType(@PathVariable("typeId") int typeId,
                                                              @RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findReceivedByType(typeId, page, size);
    }

    // 본인이 요청받은 결재 기간별 조회(페이징 처리)
    @GetMapping("/received/date-range")
    public List<QryRequestApprovalInfoDTO> findReceivedByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) throws NotLoginException {
        return qryRequestApprovalService.findReceivedByDateRange(startDate, endDate, page, size);
    }

    // 본인이 요청한 결재 내용 검색
    @GetMapping("/search")
    public List<QryRequestApprovalInfoDTO> findByContent(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        return qryRequestApprovalService.findByContent(keyword, page, size);
    }

    // 본인이 요청한 결재 승인 상태별 조회
    @GetMapping("/status/{status}")
    public List<QryRequestApprovalInfoDTO> findByStatus(
            @PathVariable("status") String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        return qryRequestApprovalService.findByStatus(status, page, size);
    }

    // 본인이 요청받은 결재 내용 검색
    @GetMapping("/received/search")
    public List<QryRequestApprovalInfoDTO> findReceivedByContent(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        return qryRequestApprovalService.findReceivedByContent(keyword, page, size);
    }

    // 본인이 요청받은 결재 승인 상태별 조회
    @GetMapping("/received/status/{status}")
    public List<QryRequestApprovalInfoDTO> findReceivedByStatus(
            @PathVariable("status") String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException {
        return qryRequestApprovalService.findReceivedByStatus(status, page, size);
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
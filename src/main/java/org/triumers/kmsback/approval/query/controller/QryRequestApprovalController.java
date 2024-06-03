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
import org.triumers.kmsback.common.exception.WrongInputValueException;

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
    public QryRequestApprovalWithEmployeeDTO findById(@PathVariable("approvalId") int approvalId) throws NotLoginException, WrongInputValueException {
        return qryRequestApprovalService.findById(approvalId);
    }

    // 본인이 요청한 결재 전체/유형별/기간별/상태별/검색 조회
    @GetMapping("/search")
    public List<QryRequestApprovalInfoDTO> findQryRequestApprovalInfo(
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException, WrongInputValueException {
        return qryRequestApprovalService.findQryRequestApprovalInfo(
                typeId,
                startDate,
                endDate,
                keyword,
                status,
                page,
                size
        );
    }

    // 본인이 요청받은 결재 단일 조회
    @GetMapping("/received/{requestApprovalId}")
    public QryRequestApprovalWithEmployeeDTO findReceivedById(@PathVariable("requestApprovalId") int requestApprovalId) throws NotLoginException, WrongInputValueException {
        return qryRequestApprovalService.findReceivedById(requestApprovalId);
    }

    // 본인이 요청받은 결재 전체/유형별/기간별/상태별/검색 조회
    @GetMapping("/received/search")
    public List<QryRequestApprovalInfoDTO> findReceivedQryRequestApprovalInfo(
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws NotLoginException, WrongInputValueException {
        return qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                typeId,
                startDate,
                endDate,
                keyword,
                status,
                page,
                size
        );
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
package org.triumers.kmsback.approval.query.service;

import jakarta.annotation.Nullable;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

import java.time.LocalDateTime;
import java.util.List;

public interface QryRequestApprovalService {
    // 본인이 요청한 결재 단일 조회
    QryRequestApprovalWithEmployeeDTO findById(int approvalId) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 전체/유형별/기간별/상태별/검색 조회
    List<QryRequestApprovalInfoDTO> findQryRequestApprovalInfo(
            @Nullable Integer typeId,
            @Nullable LocalDateTime startDate,
            @Nullable LocalDateTime endDate,
            @Nullable String keyword,
            @Nullable String status,
            @Nullable Boolean isCanceled,
            int page,
            int size
    ) throws NotLoginException, WrongInputValueException;



    // 본인이 요청받은 결재 단일 조회
    QryRequestApprovalWithEmployeeDTO findReceivedById(int requestApprovalId) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 전체/유형별/기간별/상태별/검색 조회
    List<QryRequestApprovalInfoDTO> findReceivedQryRequestApprovalInfo(
            @Nullable Integer typeId,
            @Nullable LocalDateTime startDate,
            @Nullable LocalDateTime endDate,
            @Nullable String keyword,
            @Nullable String status,
            @Nullable Boolean isCanceled,
            int page,
            int size
    ) throws NotLoginException, WrongInputValueException;
}
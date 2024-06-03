package org.triumers.kmsback.approval.query.service;

import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

import java.time.LocalDateTime;
import java.util.List;

public interface QryRequestApprovalService {
    // 본인이 요청한 결재 단일 조회
    QryRequestApprovalWithEmployeeDTO findById(int approvalId) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 전체 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findAll(int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 유형별 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findByType(int typeId, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 기간별 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 내용 검색
    List<QryRequestApprovalInfoDTO> findByContent(String keyword, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청한 결재 승인 상태별 조회
    List<QryRequestApprovalInfoDTO> findByStatus(String status, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 단일 조회
    QryRequestApprovalWithEmployeeDTO findReceivedById(int requestApprovalId) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 전체 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findAllReceived(int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 유형별 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findReceivedByType(int typeId, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 기간별 조회(페이징 처리)
    List<QryRequestApprovalInfoDTO> findReceivedByDateRange(LocalDateTime startDate, LocalDateTime endDate, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 내용 검색
    List<QryRequestApprovalInfoDTO> findReceivedByContent(String keyword, int page, int size) throws NotLoginException, WrongInputValueException;

    // 본인이 요청받은 결재 승인 상태별 조회
    List<QryRequestApprovalInfoDTO> findReceivedByStatus(String status, int page, int size) throws NotLoginException, WrongInputValueException;
}
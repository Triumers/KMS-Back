package org.triumers.kmsback.approval.query.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.approval.query.repository.QryRequestApprovalMapper;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.service.QryEmployeeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QryRequestApprovalServiceImpl implements QryRequestApprovalService {

    private final QryRequestApprovalMapper qryRequestApprovalMapper;
    private final QryEmployeeService qryEmployeeService;
    private final AuthService authService;

    @Autowired
    public QryRequestApprovalServiceImpl(QryRequestApprovalMapper qryRequestApprovalMapper, QryEmployeeService qryEmployeeService, AuthService authService) {
        this.qryRequestApprovalMapper = qryRequestApprovalMapper;
        this.qryEmployeeService = qryEmployeeService;
        this.authService = authService;
    }

    // 본인이 요청한 결재 단일 조회
    public QryRequestApprovalWithEmployeeDTO findById(int approvalId) throws NotLoginException, WrongInputValueException {
        int requesterId = authService.whoAmI().getId();
        QryRequestApprovalInfoDTO approvalInfo = qryRequestApprovalMapper.findById(requesterId, approvalId);
        if (approvalInfo == null) {
            throw new IllegalArgumentException("Approval not found for requesterId: " + requesterId + ", approvalId: " + approvalId);
        }

        return getQryRequestApprovalWithEmployeeDTO(approvalInfo);
    }

    // 본인이 요청한 결재 전체/유형별/기간별/상태별/검색 조회
    public List<QryRequestApprovalInfoDTO> findQryRequestApprovalInfo(
            @Nullable Integer typeId,
            @Nullable LocalDateTime startDate,
            @Nullable LocalDateTime endDate,
            @Nullable String keyword,
            @Nullable String status,
            int page,
            int size
    ) throws NotLoginException, WrongInputValueException {
        int requesterId = authService.whoAmI().getId();
        int offset = (page - 1) * size;
        int limit = size;

        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalMapper.findQryRequestApprovalInfo(
                requesterId,
                typeId,
                startDate,
                endDate,
                keyword,
                status,
                offset,
                limit
        );

        if (approvalInfoDTOS == null || approvalInfoDTOS.isEmpty()) {
            throw new IllegalArgumentException("No approvals found for the given criteria.");
        }

        return getQryRequestApprovalInfoDTOSWithEmployeeIdAndName(approvalInfoDTOS);
    }


    // 본인이 요청받은 결재 단일 조회
    public QryRequestApprovalWithEmployeeDTO findReceivedById(int requestApprovalId) throws NotLoginException, WrongInputValueException {
        int approverId = authService.whoAmI().getId();
        QryRequestApprovalInfoDTO approvalInfo = qryRequestApprovalMapper.findReceivedById(approverId, requestApprovalId);
        if (approvalInfo == null) {
            throw new IllegalArgumentException("Approval not found for approverId: " + approverId + ", requestApprovalId: " + requestApprovalId);
        }

        return getQryRequestApprovalWithEmployeeDTO(approvalInfo);
    }

    // 본인이 요청받은 결재 전체/유형별/기간별/상태별/검색 조회
    public List<QryRequestApprovalInfoDTO> findReceivedQryRequestApprovalInfo(
            @Nullable Integer typeId,
            @Nullable LocalDateTime startDate,
            @Nullable LocalDateTime endDate,
            @Nullable String keyword,
            @Nullable String status,
            int page,
            int size
    ) throws NotLoginException, WrongInputValueException {
        int approverId = authService.whoAmI().getId();
        int offset = (page - 1) * size;
        int limit = size;

        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalMapper.findReceivedQryRequestApprovalInfo(
                approverId,
                typeId,
                startDate,
                endDate,
                keyword,
                status,
                offset,
                limit
        );

        if (approvalInfoDTOS == null || approvalInfoDTOS.isEmpty()) {
            throw new IllegalArgumentException("No approvals found for the given criteria.");
        }

        return getQryRequestApprovalInfoDTOSWithEmployeeIdAndName(approvalInfoDTOS);
    }

    private QryRequestApprovalWithEmployeeDTO getQryRequestApprovalWithEmployeeDTO(QryRequestApprovalInfoDTO approvalInfo) throws WrongInputValueException {
        QryEmployeeDTO requester = qryEmployeeService.findEmployeeById(approvalInfo.getRequesterId());
        if (requester == null) {
            throw new IllegalArgumentException("Requester not found with id: " + approvalInfo.getRequesterId());
        }

        QryEmployeeDTO approver = qryEmployeeService.findEmployeeById(approvalInfo.getApproverId());
        if (approver == null) {
            throw new IllegalArgumentException("Approver not found with id: " + approvalInfo.getApproverId());
        }

        QryRequestApprovalWithEmployeeDTO result = new QryRequestApprovalWithEmployeeDTO();
        result.setApprovalInfo(approvalInfo);
        result.setRequester(requester);
        result.setApprover(approver);

        return result;
    }

    private List<QryRequestApprovalInfoDTO> getQryRequestApprovalInfoDTOSWithEmployeeIdAndName(List<QryRequestApprovalInfoDTO> approvalInfoDTOS) throws WrongInputValueException {
        for (QryRequestApprovalInfoDTO approvalInfoDTO : approvalInfoDTOS) {
            QryEmployeeDTO requester = qryEmployeeService.findEmployeeById(approvalInfoDTO.getRequesterId());
            if (requester == null) {
                throw new IllegalArgumentException("Requester not found with id: " + approvalInfoDTO.getRequesterId());
            }
            approvalInfoDTO.setRequesterId(requester.getId());
            approvalInfoDTO.setRequesterName(requester.getName());

            QryEmployeeDTO approver = qryEmployeeService.findEmployeeById(approvalInfoDTO.getApproverId());
            if (approver == null) {
                throw new IllegalArgumentException("Approver not found with id: " + approvalInfoDTO.getApproverId());
            }
            approvalInfoDTO.setApproverId(approver.getId());
            approvalInfoDTO.setApproverName(approver.getName());
        }

        return approvalInfoDTOS;
    }
}
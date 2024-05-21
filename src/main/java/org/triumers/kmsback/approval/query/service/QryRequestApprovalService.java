package org.triumers.kmsback.approval.query.service;

import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface QryRequestApprovalService {

    QryRequestApprovalWithEmployeeDTO findById(int requesterId, int approvalId);
    List<QryRequestApprovalInfoDTO> findAll(int requesterId, int page, int size);
    List<QryRequestApprovalInfoDTO> findByType(int requesterId, int typeId, int page, int size);
    List<QryRequestApprovalInfoDTO> findByDateRange(int requesterId, LocalDateTime startDate, LocalDateTime endDate, int page, int size);

}

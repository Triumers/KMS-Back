package org.triumers.kmsback.approval.query.repository;

import jakarta.annotation.Nullable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface QryRequestApprovalMapper {

    QryRequestApprovalInfoDTO findById(@Param("requesterId") int employeeId, @Param("approvalId") int approvalId);
    List<QryRequestApprovalInfoDTO> findQryRequestApprovalInfo(
            @Param("requesterId") int requesterId,
            @Param("typeId") @Nullable Integer typeId,
            @Param("startDate") @Nullable LocalDateTime startDate,
            @Param("endDate") @Nullable LocalDateTime endDate,
            @Param("keyword") @Nullable String keyword,
            @Param("status") @Nullable String status,
            @Param("isCanceled") @Nullable Boolean isCanceled,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    QryRequestApprovalInfoDTO findReceivedById(@Param("approverId") int employeeId, @Param("requestApprovalId") int requestApprovalId);
    List<QryRequestApprovalInfoDTO> findReceivedQryRequestApprovalInfo(
            @Param("approverId") int approverId,
            @Param("typeId") @Nullable Integer typeId,
            @Param("startDate") @Nullable LocalDateTime startDate,
            @Param("endDate") @Nullable LocalDateTime endDate,
            @Param("keyword") @Nullable String keyword,
            @Param("status") @Nullable String status,
            @Param("isCanceled") @Nullable Boolean isCanceled,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}

package org.triumers.kmsback.approval.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface QryRequestApprovalMapper {
    QryRequestApprovalInfoDTO findById(@Param("requesterId") int employeeId, @Param("approvalId") int approvalId);
    List<QryRequestApprovalInfoDTO> findAll(@Param("requesterId") int employeeId, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findByType(@Param("requesterId") int employeeId, @Param("typeId") int typeId, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findByDateRange(@Param("requesterId") int employeeId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("offset") int offset, @Param("limit") int limit);

    QryRequestApprovalInfoDTO findReceivedById(@Param("approverId") int employeeId, @Param("requestApprovalId") int requestApprovalId);
    List<QryRequestApprovalInfoDTO> findAllReceived(@Param("approverId") int employeeId, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findReceivedByType(@Param("approverId") int employeeId, @Param("typeId") int typeId, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findReceivedByDateRange(@Param("approverId") int employeeId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("offset") int offset, @Param("limit") int limit);

    List<QryRequestApprovalInfoDTO> findByContent(@Param("requesterId") int requesterId, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findByStatus(@Param("requesterId") int requesterId, @Param("status") String status, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findReceivedByContent(@Param("approverId") int approverId, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    List<QryRequestApprovalInfoDTO> findReceivedByStatus(@Param("approverId") int approverId, @Param("status") String status, @Param("offset") int offset, @Param("limit") int limit);
}

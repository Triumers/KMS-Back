package org.triumers.kmsback.approval.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalTypeRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdRequestApprovalRepository;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CmdRequestApprovalServiceImpl implements CmdRequestApprovalService {

    private final CmdApprovalRepository approvalRepository;
    private final CmdRequestApprovalRepository requestApprovalRepository;
    private final CmdEmployeeService cmdEmployeeService;
    private final CmdApprovalTypeRepository approvalTypeRepository;
    private final AuthService authService;

    @Autowired
    public CmdRequestApprovalServiceImpl(CmdApprovalRepository approvalRepository, CmdRequestApprovalRepository requestApprovalRepository, CmdEmployeeService cmdEmployeeService, CmdApprovalTypeRepository approvalTypeRepository, AuthService authService) {
        this.approvalRepository = approvalRepository;
        this.requestApprovalRepository = requestApprovalRepository;
        this.cmdEmployeeService = cmdEmployeeService;
        this.approvalTypeRepository = approvalTypeRepository;
        this.authService = authService;
    }

    @Transactional
    public void createApproval(CmdApprovalRequestDTO requestDto) throws NotLoginException {
        int requesterId = getCurrentUserId();

        // 결재 요청자 조회
        CmdEmployeeDTO requesterDTO = cmdEmployeeService.findEmployeeById(requesterId);
        Employee requester = convertToEntity(requesterDTO);

        // 결재 유형 조회
        CmdApprovalType approvalType = approvalTypeRepository.findById(requestDto.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid approval type id"));

        // Approval 엔티티 생성 및 저장
        CmdApproval approval = new CmdApproval();
        approval.setContent(requestDto.getContent());
        approval.setType(approvalType);
        approval.setRequester(requester);
        approval.setCreatedAt(LocalDateTime.now());
        CmdApproval savedApproval = approvalRepository.save(approval);

        // 결재자 조회
        CmdEmployeeDTO approverDTO = cmdEmployeeService.findEmployeeById(requestDto.getApproverId());
        Employee approver = convertToEntity(approverDTO);

        // RequestApproval 엔티티 생성 및 저장
        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApprovalOrder(1);
        requestApproval.setApprover(approver);
        requestApproval.setApproval(savedApproval);
        requestApprovalRepository.save(requestApproval);
    }

    @Transactional
    public void cancelApproval(int approvalId) throws NotLoginException {
        int requesterId = getCurrentUserId();

        CmdApproval approval = approvalRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("Approval not found for approvalId: " + approvalId));

        if (approval.getRequester().getId() != requesterId) {
            throw new IllegalArgumentException("You are not authorized to cancel this approval");
        }

        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findByApprovalIdOrderByApprovalOrderAsc(approvalId);

        Optional<CmdRequestApproval> pendingRequestApproval = requestApprovals.stream()
                .filter(ra -> ra.getIsApproved().equals("WAITING"))
                .findFirst();

        if (pendingRequestApproval.isPresent()) {
            int currentOrder = pendingRequestApproval.get().getApprovalOrder();
            requestApprovals.stream()
                    .filter(ra -> ra.getApprovalOrder() <= currentOrder)
                    .forEach(ra -> {
                        ra.setCanceled(true);
                        requestApprovalRepository.save(ra);
                    });
        } else {
            throw new IllegalStateException("Cannot cancel the approval as it has already been processed");
        }
    }

    @Transactional
    public void approveRequestApproval(int requestApprovalId) throws NotLoginException {
        int approverId = getCurrentUserId();

        CmdRequestApproval requestApproval = requestApprovalRepository.findById(requestApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Request approval not found with id: " + requestApprovalId));

        if (requestApproval.getApprover().getId() != approverId) {
            throw new IllegalArgumentException("You are not the approver for this request");
        }

        if (!requestApproval.getIsApproved().equals("WAITING")) {
            throw new IllegalStateException("Request approval has already been processed");
        }

        requestApproval.setIsApproved("APPROVED");
        requestApprovalRepository.save(requestApproval);
    }

    @Transactional
    public void rejectRequestApproval(int requestApprovalId) throws NotLoginException {
        int approverId = getCurrentUserId();

        CmdRequestApproval requestApproval = requestApprovalRepository.findById(requestApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Request approval not found with id: " + requestApprovalId));

        if (requestApproval.getApprover().getId() != approverId) {
            throw new IllegalArgumentException("You are not the approver for this request");
        }

        if (!requestApproval.getIsApproved().equals("WAITING")) {
            throw new IllegalStateException("Request approval has already been processed");
        }

        requestApproval.setIsApproved("REJECTED");
        requestApprovalRepository.save(requestApproval);
    }

    @Transactional
    public void addApproverToRequestApproval(int requestApprovalId, int newApproverId) throws NotLoginException {
        int approverId = getCurrentUserId();

        CmdRequestApproval requestApproval = requestApprovalRepository.findById(requestApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Request approval not found with id: " + requestApprovalId));

        if (requestApproval.getApprover().getId() != approverId) {
            throw new IllegalArgumentException("You are not the approver for this request");
        }

        if (!requestApproval.getIsApproved().equals("WAITING")) {
            throw new IllegalStateException("Request approval has already been processed");
        }

        CmdEmployeeDTO newApproverDTO = cmdEmployeeService.findEmployeeById(newApproverId);
        if (newApproverDTO == null) {
            throw new IllegalArgumentException("Employee not found with id: " + newApproverId);
        }

        Employee newApprover = convertToEntity(newApproverDTO);

        int nextApprovalOrder = requestApproval.getApproval().getRequestApprovals().stream()
                .mapToInt(CmdRequestApproval::getApprovalOrder)
                .max()
                .orElse(0) + 1;

        CmdRequestApproval newRequestApproval = new CmdRequestApproval();
        newRequestApproval.setApprovalOrder(nextApprovalOrder);
        newRequestApproval.setApprover(newApprover);
        newRequestApproval.setApproval(requestApproval.getApproval());
        newRequestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(newRequestApproval);

        requestApproval.setIsApproved("APPROVED");
        requestApprovalRepository.save(requestApproval);
    }

    // DTO를 엔티티로 변환하는 메서드
    private Employee convertToEntity(CmdEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setEmail(dto.getEmail());
        employee.setName(dto.getName());
        employee.setProfileImg(dto.getProfileImg());
        employee.setName(dto.getName());
        employee.setEndDate(dto.getEndDate());
        employee.setStartDate(dto.getStartDate());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setPositionId(dto.getPositionId());
        employee.setRankId(dto.getRankId());
        employee.setTeamId(dto.getTeamId());
        return employee;
    }

    // 현재 사용자의 ID를 얻는 헬퍼 메서드
    private int getCurrentUserId() throws NotLoginException {
        return authService.whoAmI().getId();
    }
}
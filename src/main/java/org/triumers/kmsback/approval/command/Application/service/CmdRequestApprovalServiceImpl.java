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
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.user.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CmdRequestApprovalServiceImpl implements CmdRequestApprovalService {

    // insert문
    // 1. 결재 대상자를 택해 권한 신청(결재)
    // ㄴ 결재 대상자는 한 명
    // ㄴ 워크스페이스 생성 요청
    // ㄴ 스터디 생성 요청
    // ㄴ 속하지 않은 워크스페이스 열람(읽기) 요청
    // approval에 우선 하나 추가하고 request_approval에 approval_order 1인 거 하나 추가하기

    // 그리고 그 요청하는 사람 레벨도 설정해야 됨... 냅다 신입사원이 신청할 수 없게
    // 날짜 시간은 선택할 수 없고 입력한 시간으로 바로 되는 걸로 -> now로 만들기
    // approver 선택하는 거는 employeeservice 쪽 메소드 끌어와서 만들기
    // 탭간 관계 id 그냥 null로 함

    private final CmdApprovalRepository approvalRepository;
    private final CmdRequestApprovalRepository requestApprovalRepository;
    private final CmdEmployeeService cmdEmployeeService;
    private final CmdApprovalTypeRepository approvalTypeRepository;

    @Autowired
    public CmdRequestApprovalServiceImpl(CmdApprovalRepository approvalRepository, CmdRequestApprovalRepository requestApprovalRepository, CmdEmployeeService cmdEmployeeService, CmdApprovalTypeRepository approvalTypeRepository) {
        this.approvalRepository = approvalRepository;
        this.requestApprovalRepository = requestApprovalRepository;
        this.cmdEmployeeService = cmdEmployeeService;
        this.approvalTypeRepository = approvalTypeRepository;
    }

    @Transactional
    public void createApproval(CmdApprovalRequestDTO requestDto, int requesterId) {

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


    // update
    // 본인이 요청한 결재 취소
//    메서드는 requesterId와 approvalId를 파라미터로 받습니다.
//    approvalRepository에서 approvalId에 해당하는 CmdApproval 객체를 찾습니다. 객체가 없으면 예외를 던집니다.
//    CmdApproval 객체의 getRequester().getId()와 requesterId가 일치하는지 확인합니다. 일치하지 않으면 예외를 던집니다. 이를 통해 본인이 요청한 결재만 취소할 수 있도록 제한합니다.
//    requestApprovalRepository에서 approvalId에 해당하는 모든 CmdRequestApproval 객체 리스트를 가져옵니다. 이때 approvalOrder로 오름차순 정렬합니다.
//    리스트에서 isApproved가 WAITING 상태인 객체를 찾습니다. 이는 아직 승인되지 않은 요청 승인 객체입니다.
//    해당 객체가 있다면:
//
//    현재 approvalOrder를 가져옵니다.
//    리스트에서 approvalOrder가 현재 approvalOrder 이하인 모든 객체를 찾습니다.
//    해당 객체들의 isCanceled를 true로 설정하고, 데이터베이스에 저장합니다.
//
//    해당 객체가 없다면, 모든 요청 승인이 이미 처리되었기 때문에 예외를 던집니다.

    @Transactional
    public void cancelApproval(int requesterId, int approvalId) {
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

    // 요청받은 결재 승인
    // request approval id 받아서
    // is_approved WAITING인 거 APPROVED로 바꾸기
    @Transactional
    public void approveRequestApproval(int approverId, int requestApprovalId) {
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

    // 요청받은 결재 승인 거부
    // request approval id 받아서
    // is_approved WAITING인 거 REJECTED로 바꾸기
    @Transactional
    public void rejectRequestApproval(int approverId, int requestApprovalId) {
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

    // 요청받은 결재 승인 후 결재 대상자 추가
    // requestapproval 생성하기 -> approval_order랑 결재자만 다르게 해서 넣기
    @Transactional
    public void addApproverToRequestApproval(int approverId, int requestApprovalId, int newApproverId) {
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

}

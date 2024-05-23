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
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.employee.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.employee.command.domain.aggregate.entity.CmdEmployee;

import java.time.LocalDateTime;

@Service
public class CmdRequestApprovalServiceImpl implements CmdRequestApprovalService{

    // insert문
    // 1. 결재 대상자를 택해 권한 신청(결재)
    // ㄴ 결재 대상자는 한 명
    // ㄴ 워크스페이스 생성 요청
    // ㄴ 스터디 생성 요청
    // ㄴ 속하지 않은 워크스페이스 열람(읽기) 요청

    // approval에 우선 하나 추가하고 request_approval에 approval_order 1인 거 하나 추가하기

    // 워크스페이스 생성 요청, 스터디 생성 요청 -> 탭간 관계에서
    // 속하지 않은 워크스페이스 열람 요청 -> 탭간 관계에서
    // -> 이거 프론트에서 해버릴까... 우선 백에서 할 거면 메소드 두 개 만들어야 됨
    // 상위 탭 - 스터디, 워크스페이스 등 큰 분류
    // 하위 탭 - 어떤 스터디인지 어떤 워크스페이스인지

    // 일단 먼저 신청할 거 4개 중 하나 고르면
//    // 탭간 관계 ID를 통해 가져와야 되는 것 - 상위 탭 ID(not null), 하위 탭 ID(nullable), team id(null)
//    // 워크스페이스/스터디 생성 요청 - 상위 탭만 고를 수 있어야 함 하위 탭 id는 그냥 null로 두고 상위 탭 테이블에서 이름 선택 -> 워크스페이스, 스터디만?
//    // 속하지 않은 워크스페이스 열람(읽기) 요청 - 상위 탭, 하위 탭 둘 다 고를 수 있어야 함 둘 다 이름 불러와야 됨... 상위 탭 선택 후 하위 탭에서 해당되는 거 선택해야 됨
    // 속하지 않는 워크스페이스 열람 요청은 그냥 링크로 받기... 이럴 거면 그냥 음 유형 고를 수 있으니 탭을 굳이 할 필요 없을 듯?

    // 그리고 그 요청하는 사람 레벨도 설정해야 됨... 냅다 신입사원이 신청할 수 없게

    // 날짜 시간은 선택할 수 없고 입력한 시간으로 바로 되는 걸로 -> now로 만들기

    // approver 선택하는 거는 employeeservice 쪽 메소드 끌어와서 만들기

    // 탭 그냥 null로 함


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
    public void createApproval(CmdApprovalRequestDTO requestDto, int requesterId){

        // 결재 요청자 조회
        CmdEmployeeDTO requesterDTO = cmdEmployeeService.findEmployeeById(requesterId);
        CmdEmployee requester = convertToEntity(requesterDTO);

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
        CmdEmployee approver = convertToEntity(approverDTO);

        // RequestApproval 엔티티 생성 및 저장
        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApprovalOrder(1);
        requestApproval.setApprover(approver);
        requestApproval.setApproval(savedApproval);
        requestApprovalRepository.save(requestApproval);
    }

    // DTO를 엔티티로 변환하는 메서드
    private CmdEmployee convertToEntity(CmdEmployeeDTO dto) {
        CmdEmployee employee = new CmdEmployee();
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




    // 2. 결재 유형 추가
    // tbl_approval_type에 값 하나 추가하기



    // update
    // 본인이 요청한 결재 취소
    // 취소 여부 수정 -> 결재 아이디 같은 컬럼들 -> request approval 테이블에 있는 is_canceled true로 바꾸기
//    // 최종 마지막이 is_approved=false 인 거 다 is_canceled = true로 바꾸면 됨...
//
//    // approvalId가 주어지면, 해당 approvalId를 가진 결재 요청 중에서 approvalOrder가 가장 큰 값을 찾습니다.
//    // -> approvalId가 주어져야 되는 게 아님!
//    // 그 중에서 isApproved가 false인 레코드가 있다면, 그 레코드와 approvalOrder가 그 이하인 모든 레코드의 isCanceled 필드를 true로 업데이트합니다.
//    // isApproved가 false인 레코드가 없다면, 예외를 던집니다.
//
////    컨트롤러에서 PUT /request-approval/{id}/cancel 요청을 받으면 CmdRequestApprovalService의 cancelApprovalRequest 메서드를 호출합니다. 여기서 {id}는 결재 요청의 ID입니다.
////    cancelApprovalRequest 메서드에서는 다음의 작업을 수행합니다:
////
////    주어진 id로 CmdRequestApproval 엔티티를 찾습니다.
////    해당 엔티티의 approvalId를 가져옵니다.
////    approvalId를 사용하여 동일한 approvalId를 가진 결재 요청 중에서 approvalOrder가 가장 큰 값을 찾습니다(findMaxOrderByApprovalId 메서드 사용).
////    그 중에서 isApproved가 false인 레코드가 있다면, 그 레코드와 approvalOrder가 그 이하인 모든 레코드의 isCanceled 필드를 true로 업데이트합니다(updateIsCanceledByApprovalIdAndOrder 메서드 사용).
////    isApproved가 false인 레코드가 없다면, 예외를 던집니다.



    // 요청받은 결재 승인


    // 요청받은 결재 승인 거부
    // -> default가 false기 때문에 우선 그렇게 두면 됨
    // 승인 대기 중 이런 거 안 만들어도 되나...?

    // 요청받은 결재 승인 후 결재 대상자 추가
    // requestapproval 생성하기 -> approval_order랑 결재자만 다르게 해서 넣기
}

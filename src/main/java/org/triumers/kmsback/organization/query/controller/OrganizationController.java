package org.triumers.kmsback.organization.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.organization.query.aggregate.vo.QryCenterVO;
import org.triumers.kmsback.organization.query.aggregate.vo.QryDepartmentVO;
import org.triumers.kmsback.organization.query.aggregate.vo.QryTeamMemberVO;
import org.triumers.kmsback.organization.query.aggregate.vo.QryTeamVO;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.service.QryCenterService;
import org.triumers.kmsback.organization.query.service.QryDepartmentService;
import org.triumers.kmsback.organization.query.service.QryTeamService;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organization/find")
public class OrganizationController {

    private final QryCenterService qryCenterService;
    private final QryDepartmentService qryDepartmentService;
    private final QryTeamService qryTeamService;

    @Autowired
    public OrganizationController(QryCenterService qryCenterService, QryDepartmentService qryDepartmentService, QryTeamService qryTeamService) {
        this.qryCenterService = qryCenterService;
        this.qryDepartmentService = qryDepartmentService;
        this.qryTeamService = qryTeamService;
    }

    @GetMapping("/center/all-detail")
    public ResponseEntity<List<QryCenterVO>> findAllTeamDetail() {

        List<QryCenterDTO> allCenter = qryCenterService.findAllCenterDetailList();
        List<QryCenterVO> allCenterVO = new ArrayList<>();

        for (QryCenterDTO centerDTO : allCenter) {
            allCenterVO.add(centerDetailToVO(centerDTO));
        }

        return ResponseEntity.status(HttpStatus.OK).body(allCenterVO);
    }

    private QryCenterVO centerDetailToVO(QryCenterDTO qryCenterDTO) {
        QryCenterVO qryCenterVO = new QryCenterVO();
        qryCenterVO.setId(qryCenterDTO.getId());
        qryCenterVO.setName(qryCenterDTO.getName());

        List<QryDepartmentVO> belongDepartmentVO = new ArrayList<>();
        for (QryDepartmentDTO departmentDTO : qryCenterDTO.getBelongDepartments()) {
            belongDepartmentVO.add(departmentDetailToVO(departmentDTO));
        }
        qryCenterVO.setBelongDepartments(belongDepartmentVO);

        return qryCenterVO;
    }

    private QryDepartmentVO departmentDetailToVO(QryDepartmentDTO departmentDTO) {
        QryDepartmentVO departmentVO = new QryDepartmentVO();
        departmentVO.setId(departmentDTO.getId());
        departmentVO.setName(departmentDTO.getName());

        List<QryTeamVO> belongTeamVOList = new ArrayList<>();

        for (QryTeamDTO teamDTO : departmentDTO.getBelongTeams()) {
            belongTeamVOList.add(teamDetailToVO(teamDTO));
        }
        departmentVO.setBelongTeams(belongTeamVOList);

        return departmentVO;
    }

    private QryTeamVO teamDetailToVO(QryTeamDTO qryTeamDTO) {
        QryTeamVO qryTeamVO = new QryTeamVO();
        qryTeamVO.setId(qryTeamDTO.getId());
        qryTeamVO.setName(qryTeamDTO.getName());

        List<QryTeamMemberVO> teamMemberList = new ArrayList<>();
        for (QryEmployeeDTO employee : qryTeamDTO.getTeamMembers()) {
            teamMemberList.add(new QryTeamMemberVO(employee.getId(), employee.getName(), employee.getProfileImg()));
        }
        qryTeamVO.setMembers(teamMemberList);

        return qryTeamVO;
    }
}

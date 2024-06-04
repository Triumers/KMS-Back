package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryDepartment;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryDepartmentServiceImpl implements QryDepartmentService {

    private final OrganizationMapper organizationMapper;
    private final QryCenterService qryCenterService;
    private final QryTeamService qryTeamService;

    @Autowired
    public QryDepartmentServiceImpl(OrganizationMapper organizationMapper,
                                    QryCenterService qryCenterService,
                                    @Lazy QryTeamService qryTeamService) {
        this.organizationMapper = organizationMapper;
        this.qryCenterService = qryCenterService;
        this.qryTeamService = qryTeamService;
    }

    @Override
    public QryDepartmentDTO findDepartmentById(int id) {
        return departmentToDto(organizationMapper.findDepartmentById(id));
    }

    // 소속된 팀, 사원 포함 조회
    @Override
    public QryDepartmentDTO findDepartmentDetailById(int id) {
        QryDepartment department = organizationMapper.findDepartmentById(id);
        List<QryTeamDTO> belongTeams = qryTeamService.findTeamDetailListByDepartmentId(id);
        return new QryDepartmentDTO(department.getId(), department.getName(), department.getCenterId(), null, belongTeams);
    }

    @Override
    public List<QryDepartmentDTO> findDepartmentListByName(String departmentName) {
        List<QryDepartment> departmentList = organizationMapper.findDepartmentListByName(departmentName);
        List<QryDepartmentDTO> departmentDtoList = new ArrayList<>();

        for (QryDepartment department : departmentList) {
            departmentDtoList.add(departmentToDto(department));
        }

        return departmentDtoList;
    }

    @Override
    public List<QryDepartmentDTO> findDepartmentListByCenterId(int centerId) {
        List<QryDepartment> departmentList = organizationMapper.findDepartmentListByCenterId(centerId);
        List<QryDepartmentDTO> departmentDtoList = new ArrayList<>();

        for (QryDepartment department : departmentList) {
            departmentDtoList.add(departmentToDto(department));
        }

        return departmentDtoList;
    }

    @Override
    public List<QryDepartmentDTO> findDepartmentDetailListByCenterId(int centerId) {
        List<QryDepartment> departmentList = organizationMapper.findDepartmentListByCenterId(centerId);
        List<QryDepartmentDTO> departmentDtoList = new ArrayList<>();

        for (QryDepartment department : departmentList) {
            List<QryTeamDTO> belongTeams = qryTeamService.findTeamDetailListByDepartmentId(department.getId());
            departmentDtoList.add(new QryDepartmentDTO(department.getId(), department.getName(), department.getCenterId(), null, belongTeams));
        }

        return departmentDtoList;
    }

    private QryDepartmentDTO departmentToDto(QryDepartment department) {
        String centerName = qryCenterService.findCenterById(department.getCenterId()).getName();
        return new QryDepartmentDTO(department.getId(), department.getName(), department.getCenterId(), centerName, null);
    }
}

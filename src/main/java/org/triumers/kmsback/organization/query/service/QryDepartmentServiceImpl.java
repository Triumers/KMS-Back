package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryDepartment;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryDepartmentServiceImpl implements QryDepartmentService {

    private final OrganizationMapper organizationMapper;
    private final QryCenterService qryCenterService;

    @Autowired
    public QryDepartmentServiceImpl(OrganizationMapper organizationMapper, QryCenterService qryCenterService) {
        this.organizationMapper = organizationMapper;
        this.qryCenterService = qryCenterService;
    }

    @Override
    public QryDepartmentDTO findDepartmentById(int id) {
        return departmentToDto(organizationMapper.findDepartmentById(id));
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

    private QryDepartmentDTO departmentToDto(QryDepartment department) {
        String centerName = qryCenterService.findCenterById(department.getCenterId()).getName();
        return new QryDepartmentDTO(department.getId(), department.getName(), department.getCenterId(), centerName);
    }
}

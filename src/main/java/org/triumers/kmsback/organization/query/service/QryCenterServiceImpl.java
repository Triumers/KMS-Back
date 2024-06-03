package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryCenter;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryCenterServiceImpl implements QryCenterService {

    private final OrganizationMapper organizationMapper;
    private final QryDepartmentService qryDepartmentService;

    @Autowired
    public QryCenterServiceImpl(OrganizationMapper organizationMapper,
                                @Lazy QryDepartmentService qryDepartmentService) {
        this.organizationMapper = organizationMapper;
        this.qryDepartmentService = qryDepartmentService;
    }

    @Override
    public QryCenterDTO findCenterById(int id) {

        QryCenter center = organizationMapper.findCenterById(id);

        return centerToDTO(center);
    }

    @Override
    public QryCenterDTO findCenterDetailById(int id) {

        QryCenter center = organizationMapper.findCenterById(id);
        List<QryDepartmentDTO> belongDepartments = qryDepartmentService.findDepartmentDetailListByCenterId(id);

        return new QryCenterDTO(center.getId(), center.getName(), belongDepartments);
    }

    @Override
    public List<QryCenterDTO> findCenterListByName(String name) {
        List<QryCenter> centerList = organizationMapper.findCenterListByName(name);
        List<QryCenterDTO> centerDtoList = new ArrayList<>();

        for (QryCenter center : centerList) {
            centerDtoList.add(centerToDTO(center));
        }

        return centerDtoList;
    }

    @Override
    public List<QryCenterDTO> findAllCenterList() {

        List<QryCenter> centerList = organizationMapper.findAllCenter();
        List<QryCenterDTO> centerDtoList = new ArrayList<>();

        for (QryCenter center : centerList) {
            centerDtoList.add(centerToDTO(center));
        }

        return centerDtoList;
    }

    @Override
    public List<QryCenterDTO> findAllCenterDetailList() {

        List<QryCenter> centerList = organizationMapper.findAllCenter();
        List<QryCenterDTO> centerDtoList = new ArrayList<>();

        for (QryCenter center : centerList) {
            List<QryDepartmentDTO> belongDepartments = qryDepartmentService.findDepartmentDetailListByCenterId(center.getId());
            centerDtoList.add(new QryCenterDTO(center.getId(), center.getName(), belongDepartments));
        }

        return centerDtoList;
    }

    private QryCenterDTO centerToDTO(QryCenter center) {
        return new QryCenterDTO(center.getId(), center.getName(), null);
    }
}

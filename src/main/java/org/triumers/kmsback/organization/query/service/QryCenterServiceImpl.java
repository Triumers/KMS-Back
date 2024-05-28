package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryCenter;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryCenterServiceImpl implements QryCenterService {

    private final OrganizationMapper organizationMapper;

    @Autowired
    public QryCenterServiceImpl(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    public QryCenterDTO findCenterById(int id) {

        QryCenter center = organizationMapper.findCenterById(id);

        return centerToDTO(center);
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

    private QryCenterDTO centerToDTO(QryCenter center) {
        return new QryCenterDTO(center.getId(), center.getName());
    }
}

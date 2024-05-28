package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryCenter;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

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

    private QryCenterDTO centerToDTO(QryCenter center) {
        return new QryCenterDTO(center.getId(), center.getName());
    }
}

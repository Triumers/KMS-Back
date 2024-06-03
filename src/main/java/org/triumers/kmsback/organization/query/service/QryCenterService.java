package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.organization.query.dto.QryCenterDTO;

import java.util.List;

public interface QryCenterService {
    QryCenterDTO findCenterById(int id);

    QryCenterDTO findCenterDetailById(int id);

    List<QryCenterDTO> findCenterListByName(String name);

    List<QryCenterDTO> findAllCenterList();

    List<QryCenterDTO> findAllCenterDetailList();
}

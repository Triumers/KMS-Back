package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.organization.query.dto.QryCenterDTO;

import java.util.List;

public interface QryCenterService {
    QryCenterDTO findCenterById(int id);

    List<QryCenterDTO> findCenterListByName(String name);
}

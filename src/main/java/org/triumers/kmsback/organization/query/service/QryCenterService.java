package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.organization.query.dto.QryCenterDTO;

public interface QryCenterService {
    QryCenterDTO findCenterById(int id);
}

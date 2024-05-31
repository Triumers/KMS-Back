package org.triumers.kmsback.group.query.service;

import org.triumers.kmsback.group.query.dto.QryGroupDTO;

import java.util.List;

public interface QryGroupService {

    List<QryGroupDTO> findGroupByEmployeeId(int employeeId);

    List<Integer> findGroupIdByEmployeeId(int id);
}

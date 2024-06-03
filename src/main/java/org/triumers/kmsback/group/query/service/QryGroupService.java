package org.triumers.kmsback.group.query.service;

import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;

import java.util.List;

public interface QryGroupService {

    List<QryGroupDTO> findGroupByEmployeeId() throws NotLoginException;

    List<Integer> findGroupIdByEmployeeId(int id);
}

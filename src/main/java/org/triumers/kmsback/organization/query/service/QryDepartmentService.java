package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;

import java.util.List;

public interface QryDepartmentService {
    QryDepartmentDTO findDepartmentById(int id);

    QryDepartmentDTO findDepartmentDetailById(int id);

    List<QryDepartmentDTO> findDepartmentListByName(String departmentName);

    List<QryDepartmentDTO> findDepartmentListByCenterId(int centerId);

    List<QryDepartmentDTO> findDepartmentDetailListByCenterId(int centerId);
}

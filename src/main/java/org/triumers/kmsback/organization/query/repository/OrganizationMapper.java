package org.triumers.kmsback.organization.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.organization.query.aggregate.entity.QryCenter;
import org.triumers.kmsback.organization.query.aggregate.entity.QryDepartment;

import java.util.List;

@Mapper
public interface OrganizationMapper {

    QryCenter findCenterById(int id);

    List<QryCenter> findCenterListByName(String name);

    QryDepartment findDepartmentById(int id);

    List<QryDepartment> findDepartmentListByName(String name);

    List<QryDepartment> findDepartmentListByCenterId(int centerId);
}

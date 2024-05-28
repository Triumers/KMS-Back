package org.triumers.kmsback.organization.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.organization.query.aggregate.entity.QryCenter;

@Mapper
public interface OrganizationMapper {

    QryCenter findCenterById(int id);
}

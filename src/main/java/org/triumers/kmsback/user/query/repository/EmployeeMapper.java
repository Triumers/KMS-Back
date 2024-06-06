package org.triumers.kmsback.user.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.user.query.aggregate.entity.QryEmployee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    QryEmployee findById(int id);

    List<QryEmployee> findAllEmployee();

    QryEmployee findByEmail(String email);

    List<QryEmployee> findByName(String name);

    List<QryEmployee> findByTeamId(int teamId);

    List<QryEmployee> findSimpleInfoByTeamId(int teamId);

    QryEmployee findByIdIncludeEnd(int id);
}

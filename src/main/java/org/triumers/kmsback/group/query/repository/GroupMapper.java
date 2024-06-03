package org.triumers.kmsback.group.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;

import java.util.List;

@Mapper
public interface GroupMapper {
    List<QryGroupDTO> findGroupByEmployeeId(int employeeId);

    List<Integer> findGroupIdByEmployeeId(int employeeId);
}

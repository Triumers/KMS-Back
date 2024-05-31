package org.triumers.kmsback.group.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;
import org.triumers.kmsback.group.query.repository.GroupMapper;

import java.util.List;

@Service
public class QryGroupServiceImpl implements QryGroupService {

    @Autowired
    private final GroupMapper groupMapper;

    @Autowired
    public QryGroupServiceImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public List<QryGroupDTO> findGroupByEmployeeId(int employeeId) {
        return groupMapper.findGroupByEmployeeId(employeeId);
    }

    @Override
    public List<Integer> findGroupIdByEmployeeId(int employeeId) {
        return groupMapper.findGroupIdByEmployeeId(employeeId);
    }
}

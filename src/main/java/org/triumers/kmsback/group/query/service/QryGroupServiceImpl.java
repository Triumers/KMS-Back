package org.triumers.kmsback.group.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;
import org.triumers.kmsback.group.query.repository.GroupMapper;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.util.List;

@Service
public class QryGroupServiceImpl implements QryGroupService {

    @Autowired
    private final GroupMapper groupMapper;
    private final AuthService authService;

    @Autowired
    public QryGroupServiceImpl(GroupMapper groupMapper, AuthService authService) {
        this.groupMapper = groupMapper;
        this.authService = authService;
    }

    @Override
    public List<QryGroupDTO> findGroupByEmployeeId() throws NotLoginException {

        Employee employee = authService.whoAmI();
        return groupMapper.findGroupByEmployeeId(employee.getId());
    }

    @Override
    public List<Integer> findGroupIdByEmployeeId(int employeeId) {
        return groupMapper.findGroupIdByEmployeeId(employeeId);
    }
}

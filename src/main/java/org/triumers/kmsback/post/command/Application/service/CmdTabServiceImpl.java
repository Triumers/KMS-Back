package org.triumers.kmsback.post.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.post.command.domain.repository.CmdJoinEmployeeRepository;

@Service
public class CmdTabServiceImpl implements CmdTabService{

    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;

    @Autowired
    public CmdTabServiceImpl(CmdJoinEmployeeRepository cmdJoinEmployeeRepository) {
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
    }

    @Override
    public CmdJoinEmployee addEmployeeTab(CmdJoinEmployeeDTO employee) {
        CmdJoinEmployee newEmployee = new CmdJoinEmployee(employee.getIsLeader(),employee.getEmployeeId(),
                                                          employee.getTabId());
        return cmdJoinEmployeeRepository.save(newEmployee);
    }

    @Override
    public CmdJoinEmployee deleteEmployeeTab(CmdJoinEmployeeDTO employee) {

        CmdJoinEmployee deleteEmployee = cmdJoinEmployeeRepository
                                         .findByEmployeeIdAndTabId(employee.getEmployeeId(), employee.getTabId());

        cmdJoinEmployeeRepository.deleteById(deleteEmployee.getId());

        return deleteEmployee;
    }
}

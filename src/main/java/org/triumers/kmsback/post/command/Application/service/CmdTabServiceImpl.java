package org.triumers.kmsback.post.command.Application.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabRelation;
import org.triumers.kmsback.post.command.domain.repository.CmdJoinEmployeeRepository;
import org.triumers.kmsback.post.command.domain.repository.CmdTabRelationRepository;

@Service
public class CmdTabServiceImpl implements CmdTabService{

    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;
    private final CmdTabRelationRepository cmdTabRelationRepository;

    @Autowired
    public CmdTabServiceImpl(CmdJoinEmployeeRepository cmdJoinEmployeeRepository, CmdTabRelationRepository cmdTabRelationRepository) {
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
        this.cmdTabRelationRepository = cmdTabRelationRepository;
    }

    @Override
    @Transactional
    public CmdJoinEmployee addEmployeeTab(CmdJoinEmployeeDTO employee) {
        CmdJoinEmployee newEmployee = new CmdJoinEmployee(employee.getIsLeader(),employee.getEmployeeId(),
                                                          employee.getTabId());
        return cmdJoinEmployeeRepository.save(newEmployee);
    }

    @Override
    @Transactional
    public CmdJoinEmployee deleteEmployeeTab(CmdJoinEmployeeDTO employee) {

        CmdJoinEmployee deleteEmployee = cmdJoinEmployeeRepository
                                         .findByEmployeeIdAndTabId(employee.getEmployeeId(), employee.getTabId());

        cmdJoinEmployeeRepository.deleteById(deleteEmployee.getId());

        return deleteEmployee;
    }

    @Override
    @Transactional
    public CmdTabRelation registTab(CmdTabRelationDTO tabRelation, int employeeId) {

        CmdTabRelation newTab = new CmdTabRelation(tabRelation.getIsPublic(), tabRelation.getBottomTabId(),
                                                   tabRelation.getTopTabId());
        cmdTabRelationRepository.save(newTab);

        CmdJoinEmployeeDTO joinEmployee = new CmdJoinEmployeeDTO(true, employeeId, newTab.getId());
        addEmployeeTab(joinEmployee);

        return newTab;
    }
}

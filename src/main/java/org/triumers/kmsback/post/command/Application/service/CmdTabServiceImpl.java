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
    public CmdJoinEmployeeDTO addEmployeeTab(CmdJoinEmployeeDTO employee) {
        CmdJoinEmployee newEmployee = new CmdJoinEmployee(employee.getIsLeader(),employee.getEmployeeId(),
                                                          employee.getTabId());
        cmdJoinEmployeeRepository.save(newEmployee);

        CmdJoinEmployeeDTO joinEmployeeDTO = new CmdJoinEmployeeDTO(newEmployee.getId(), newEmployee.getIsLeader(), newEmployee.getEmployeeId(), newEmployee.getTabId());
        return joinEmployeeDTO;
    }

    @Override
    @Transactional
    public CmdJoinEmployeeDTO deleteEmployeeTab(CmdJoinEmployeeDTO employee) {

        CmdJoinEmployee deleteEmployee = cmdJoinEmployeeRepository
                                         .findByEmployeeIdAndTabId(employee.getEmployeeId(), employee.getTabId());

        cmdJoinEmployeeRepository.deleteById(deleteEmployee.getId());

        CmdJoinEmployeeDTO deleteEmployeeDTO = new CmdJoinEmployeeDTO(deleteEmployee.getId(), deleteEmployee.getIsLeader(), deleteEmployee.getEmployeeId(), deleteEmployee.getTabId());
        return deleteEmployeeDTO;
    }

    @Override
    @Transactional
    public CmdTabRelationDTO registTab(CmdTabRelationDTO tabRelation, int employeeId) {

        CmdTabRelation newTab = new CmdTabRelation(tabRelation.getIsPublic(), tabRelation.getBottomTabId(),
                                                   tabRelation.getTopTabId(), tabRelation.getTeamId());
        cmdTabRelationRepository.save(newTab);

        CmdJoinEmployeeDTO joinEmployee = new CmdJoinEmployeeDTO(true, employeeId, newTab.getId());
        addEmployeeTab(joinEmployee);

        CmdTabRelationDTO newTabRelation = new CmdTabRelationDTO(newTab.getId(), newTab.getIsPublic(),
                newTab.getBottomTabId(), newTab.getTopTabId(), newTab.getTeamId());

        return newTabRelation;
    }
}

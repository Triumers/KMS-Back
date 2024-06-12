package org.triumers.kmsback.tab.command.Application.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.tab.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdTabBottom;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdTabRelation;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdTabTop;
import org.triumers.kmsback.tab.command.domain.repository.CmdJoinEmployeeRepository;
import org.triumers.kmsback.tab.command.domain.repository.CmdTabBottomRepository;
import org.triumers.kmsback.tab.command.domain.repository.CmdTabRelationRepository;
import org.triumers.kmsback.tab.command.domain.repository.CmdTabTopRepository;

@Service
public class CmdTabServiceImpl implements CmdTabService {

    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;
    private final CmdTabRelationRepository cmdTabRelationRepository;
    private final CmdTabTopRepository cmdTabTopRepository;
    private final CmdTabBottomRepository cmdTabBottomRepository;


    @Autowired
    public CmdTabServiceImpl(CmdJoinEmployeeRepository cmdJoinEmployeeRepository, CmdTabRelationRepository cmdTabRelationRepository, CmdTabTopRepository cmdTabTopRepository, CmdTabBottomRepository cmdTabBottomRepository) {
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
        this.cmdTabRelationRepository = cmdTabRelationRepository;
        this.cmdTabTopRepository = cmdTabTopRepository;
        this.cmdTabBottomRepository = cmdTabBottomRepository;
    }

    @Override
    @Transactional
    public CmdJoinEmployeeDTO addEmployeeTab(CmdJoinEmployeeDTO employee) {

        CmdJoinEmployee newEmployee = cmdJoinEmployeeRepository.
                findByEmployeeIdAndTabId(employee.getEmployeeId(), employee.getTabId());

        if(newEmployee == null){
            newEmployee = new CmdJoinEmployee(employee.getIsLeader(),employee.getEmployeeId(),
                    employee.getTabId());
            cmdJoinEmployeeRepository.save(newEmployee);
        }

        CmdJoinEmployeeDTO joinEmployeeDTO = new CmdJoinEmployeeDTO(newEmployee.getId(), newEmployee.getIsLeader(), newEmployee.getEmployeeId(), newEmployee.getTabId());
        return joinEmployeeDTO;
    }

    @Override
    @Transactional
    public CmdJoinEmployeeDTO deleteEmployeeTab(CmdJoinEmployeeDTO employee) {

        CmdJoinEmployee deleteEmployee = cmdJoinEmployeeRepository
                                         .findByEmployeeIdAndTabId(employee.getEmployeeId(), employee.getTabId());

        cmdJoinEmployeeRepository.deleteById(deleteEmployee.getId());

        CmdJoinEmployeeDTO deleteEmployeeDTO = new CmdJoinEmployeeDTO(deleteEmployee.getId(), deleteEmployee.getIsLeader(),
                                                                      deleteEmployee.getEmployeeId(), deleteEmployee.getTabId());
        return deleteEmployeeDTO;
    }

    @Override
    @Transactional
    public CmdTabRelationDTO registTab(CmdTabRelationDTO tabRelation, int employeeId) {

        CmdTabDTO topTab = registTopTab(tabRelation.getTopTab());
        CmdTabDTO bottomTab = registBottomTab(tabRelation.getBottomTab());

        CmdTabRelation newTab = cmdTabRelationRepository.findByBottomTabIdAndTopTabIdAndTeamId(bottomTab.getId(), topTab.getId(), tabRelation.getTeamId());

        if(newTab == null){
            newTab = new CmdTabRelation(tabRelation.getIsPublic(), bottomTab.getId(),
                    topTab.getId(), tabRelation.getTeamId());
            cmdTabRelationRepository.save(newTab);

            CmdJoinEmployeeDTO joinEmployee = new CmdJoinEmployeeDTO(true, employeeId, newTab.getId());
            addEmployeeTab(joinEmployee);
        }

        CmdTabRelationDTO newTabRelation = new CmdTabRelationDTO(newTab.getId(), newTab.getIsPublic(),
                bottomTab, topTab, newTab.getTeamId());

        return newTabRelation;
    }

    @Override
    public String getTabName(int id) {

        CmdTabRelation tabRelation = cmdTabRelationRepository.findById(id);
        if(tabRelation.getBottomTabId() != null){
            String tabName = cmdTabBottomRepository.findNameById(tabRelation.getBottomTabId());
            return tabName;
        }

        String tabName = cmdTabTopRepository.findNameById(tabRelation.getTopTabId());
        return tabName;
    }

    public CmdTabDTO registTopTab(CmdTabDTO top) {

        CmdTabTop tabTop = cmdTabTopRepository.getByName(top.getName());

        if(tabTop == null){
            tabTop = new CmdTabTop(top.getName());
            cmdTabTopRepository.save(tabTop);
        }
        top.setId(tabTop.getId());

        return top;
    }

    public CmdTabDTO registBottomTab(CmdTabDTO bottom) {

        if(bottom.getName() == null)
            return bottom;

        CmdTabBottom tabBottom = cmdTabBottomRepository.getByName(bottom.getName());

        if(tabBottom == null){
            tabBottom = new CmdTabBottom(bottom.getName());
            cmdTabBottomRepository.save(tabBottom);
        }
        bottom.setId(tabBottom.getId());

        return bottom;
    }
}

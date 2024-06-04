package org.triumers.kmsback.organization.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdCenter;
import org.triumers.kmsback.organization.command.domain.repository.CenterRepository;

@Service
public class CmdCenterServiceImpl implements CmdCenterService {

    private final CenterRepository centerRepository;

    @Autowired
    public CmdCenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    public int addCenter(CmdCenterDTO centerDTO) {
        CmdCenter center = new CmdCenter();
        center.setName(centerDTO.getName());
        return centerRepository.save(center).getId();
    }

    @Override
    public void editCenterName(CmdCenterDTO centerDTO) {
        CmdCenter center = centerRepository.findById(centerDTO.getId());
        center.setName(centerDTO.getName());
        centerRepository.save(center);
    }

    @Override
    public void removeCenterById(CmdCenterDTO centerDTO) {
        centerRepository.delete(centerRepository.findById(centerDTO.getId()));
    }
}

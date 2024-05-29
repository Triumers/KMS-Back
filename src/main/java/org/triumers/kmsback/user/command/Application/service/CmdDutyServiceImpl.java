package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdPosition;
import org.triumers.kmsback.user.command.domain.repository.PositionRepository;

@Service
public class CmdDutyServiceImpl implements CmdDutyService {

    private final PositionRepository positionRepository;

    @Autowired
    public CmdDutyServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public int addPosition(CmdPositionDTO positionDTO) {
        return positionRepository.save(new CmdPosition(positionDTO.getId(), positionDTO.getName())).getId();
    }

    @Override
    public void editPositionName(CmdPositionDTO positionDTO) {
        CmdPosition position = positionRepository.findById(positionDTO.getId());
        position.setName(positionDTO.getName());
        positionRepository.save(position);
    }

    @Override
    public void removePosition(CmdPositionDTO positionDTO) {
        CmdPosition position = positionRepository.findById(positionDTO.getId());
        positionRepository.delete(position);
    }
}

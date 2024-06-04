package org.triumers.kmsback.user.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdPosition;

@Repository
public interface PositionRepository extends JpaRepository<CmdPosition, Integer> {

    CmdPosition findById(int id);
}

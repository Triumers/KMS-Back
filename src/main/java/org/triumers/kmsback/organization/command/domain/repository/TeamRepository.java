package org.triumers.kmsback.organization.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdTeam;

@Repository
public interface TeamRepository extends JpaRepository<CmdTeam, Integer> {

    CmdTeam findById(int id);
}

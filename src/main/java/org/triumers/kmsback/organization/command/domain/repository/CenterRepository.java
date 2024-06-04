package org.triumers.kmsback.organization.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdCenter;

@Repository
public interface CenterRepository extends JpaRepository<CmdCenter, Integer> {

    CmdCenter findById(int id);
}

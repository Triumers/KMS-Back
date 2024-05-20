package org.triumers.kmsback.tab.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdTabTop;

@Repository
public interface CmdTabTopRepository extends JpaRepository<CmdTabTop, Integer> {
    CmdTabTop getByName(String name);
}

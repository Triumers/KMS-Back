package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabBottom;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabTop;

@Repository
public interface CmdTabBottomRepository extends JpaRepository<CmdTabBottom, Integer> {
    CmdTabBottom getByName(String name);
}

package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTag;

@Repository
public interface CmdTagRepository extends JpaRepository<CmdTag, Integer> {


    CmdTag getByName(String name);
}

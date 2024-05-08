package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

public interface CmdPostRepository  extends JpaRepository<CmdPost, Integer> {

}

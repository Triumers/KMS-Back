package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

import java.util.Optional;

@Repository
public interface CmdPostRepository  extends JpaRepository<CmdPost, Integer> {

    @Query("select A.authorId from CmdPost A where A.id = :originId")
    Integer findAuthorIdById(int originId);
}

package org.triumers.kmsback.comment.command.Domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.CmdComment;

import java.util.List;


public interface CommentRepository extends JpaRepository<CmdComment, Integer> {

    CmdComment findById(int id);
}
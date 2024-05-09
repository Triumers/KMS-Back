package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

public interface CmdPostService {
    CmdPost registPost(CmdPostAndTagsDTO post);

    CmdPost modifyPost(CmdPostAndTagsDTO post);

    CmdPost deletePost(int postId);
}

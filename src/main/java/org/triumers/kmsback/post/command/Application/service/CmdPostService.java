package org.triumers.kmsback.post.command.Application.service;

import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

public interface CmdPostService {
    CmdPost registPost(CmdPostDTO post);
}

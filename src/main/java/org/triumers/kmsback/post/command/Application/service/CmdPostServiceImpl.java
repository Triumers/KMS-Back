package org.triumers.kmsback.post.command.Application.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;
import org.triumers.kmsback.post.command.domain.repository.CmdPostRepository;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final ModelMapper mapper;
    private final CmdPostRepository cmdPostRepository;

    @Autowired
    public CmdPostServiceImpl(ModelMapper mapper, CmdPostRepository cmdPostRepository) {
        this.mapper = mapper;
        this.cmdPostRepository = cmdPostRepository;
    }

    @Override
    public CmdPost registPost(CmdPostDTO post) {

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CmdPost registPost = mapper.map(post, CmdPost.class);
        System.out.println(registPost);

        return cmdPostRepository.save(registPost);
    }
}

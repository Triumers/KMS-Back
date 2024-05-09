package org.triumers.kmsback.post.command.Application.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTagDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPostTag;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTag;
import org.triumers.kmsback.post.command.domain.repository.CmdPostRepository;
import org.triumers.kmsback.post.command.domain.repository.CmdPostTagRepository;
import org.triumers.kmsback.post.command.domain.repository.CmdTagRepository;

import java.util.List;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final ModelMapper mapper;
    private final CmdPostRepository cmdPostRepository;
    private final CmdTagRepository cmdTagRepository;
    private final CmdPostTagRepository cmdPostTagRepository;

    @Autowired
    public CmdPostServiceImpl(ModelMapper mapper, CmdPostRepository cmdPostRepository,
                              CmdTagRepository cmdTagRepository, CmdPostTagRepository cmdPostTagRepository) {
        this.mapper = mapper;
        this.cmdPostRepository = cmdPostRepository;
        this.cmdTagRepository = cmdTagRepository;
        this.cmdPostTagRepository = cmdPostTagRepository;
    }

    @Override
    public CmdPost registPost(CmdPostAndTagsDTO post) {

        CmdPost registPost = new CmdPost(post.getTitle(), post.getContent(), post.getCreatedAt(),
                                         post.getAuthorId(), post.getTabRelationId());
        cmdPostRepository.save(registPost);
        System.out.println(registPost.getId());

        List<CmdTagDTO> tags = post.getTags();
        for (int i = 0; i < tags.size(); i++) {
            CmdTag tag = new CmdTag(tags.get(i).getName());
            cmdTagRepository.save(tag);

            CmdPostTag postTag = new CmdPostTag(tag.getId(), registPost.getId());
            cmdPostTagRepository.save(postTag);
        }

        return registPost;
    }

    @Override
    public CmdPost modifyPost(CmdPostDTO post) {
        return null;
    }

    @Override
    public CmdPost deletePost(int postId) {
        return null;
    }

//    @Override
//    public CmdPost modifyPost(CmdPostDTO post) {
//
//        CmdPost modifypost = registPost(post);
//        CmdPost originPost = cmdPostRepository.findById(post.getOriginId()).orElseThrow(IllegalArgumentException::new);
//        originPost.setRecentId(modifypost.getId());
//
//        return cmdPostRepository.save(originPost);
//
//    }
//
//    @Override
//    public CmdPost deletePost(int postId) {
//        return null;
//    }
}

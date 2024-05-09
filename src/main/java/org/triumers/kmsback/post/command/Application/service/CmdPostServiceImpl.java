package org.triumers.kmsback.post.command.Application.service;

import jakarta.transaction.Transactional;
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
    @Transactional
    public CmdPost registPost(CmdPostAndTagsDTO post) {

        CmdPost registPost = new CmdPost(post.getTitle(), post.getContent(), post.getCreatedAt(),
                                         post.getAuthorId(), post.getOriginId(), post.getTabRelationId());
        cmdPostRepository.save(registPost);

        registTag(post.getTags(), registPost.getId());

        return registPost;
    }

    public void registTag(List<CmdTagDTO> tags, int postId){

        for (int i = 0; i < tags.size(); i++) {
            CmdTagDTO tagDTO = tags.get(i);

            CmdTag tag = new CmdTag(tagDTO.getId(),tagDTO.getName());
            cmdTagRepository.save(tag);

            CmdPostTag postTag = new CmdPostTag(tag.getId(), postId);
            cmdPostTagRepository.save(postTag);
        }
    }

    @Override
    public CmdPost modifyPost(CmdPostAndTagsDTO post) {

        CmdPost modifypost = registPost(post);
        CmdPost originPost = cmdPostRepository.findById(post.getOriginId()).orElseThrow(IllegalArgumentException::new);
        originPost.setRecentId(modifypost.getId());

        return cmdPostRepository.save(originPost);

    }

    @Override
    public CmdPost deletePost(int postId) {
        return null;
    }

}

package org.triumers.kmsback.post.command.Application.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.domain.aggregate.entity.*;
import org.triumers.kmsback.post.command.domain.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final CmdPostRepository cmdPostRepository;
    private final CmdTagRepository cmdTagRepository;
    private final CmdPostTagRepository cmdPostTagRepository;
    private final CmdLikeRepository cmdLikeRepository;
    private final CmdFavoritesRepository cmdFavoritesRepository;

    @Autowired
    public CmdPostServiceImpl(CmdPostRepository cmdPostRepository,
                              CmdTagRepository cmdTagRepository, CmdPostTagRepository cmdPostTagRepository, CmdLikeRepository cmdLikeRepository, CmdFavoritesRepository cmdFavoritesRepository) {
        this.cmdPostRepository = cmdPostRepository;
        this.cmdTagRepository = cmdTagRepository;
        this.cmdPostTagRepository = cmdPostTagRepository;
        this.cmdLikeRepository = cmdLikeRepository;
        this.cmdFavoritesRepository = cmdFavoritesRepository;
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

    public void registTag(List<CmdTagDTO> tags, int postId) {

        for (int i = 0; i < tags.size(); i++) {
            CmdTagDTO tagDTO = tags.get(i);

            CmdTag tag = new CmdTag(tagDTO.getId(), tagDTO.getName());
            cmdTagRepository.save(tag);

            CmdPostTag postTag = new CmdPostTag(tag.getId(), postId);
            cmdPostTagRepository.save(postTag);
        }
    }

    @Override
    @Transactional
    public CmdPost modifyPost(CmdPostAndTagsDTO post) {

        CmdPost modifypost = registPost(post);
        CmdPost originPost = cmdPostRepository.findById(post.getOriginId()).orElseThrow(IllegalArgumentException::new);
        originPost.setRecentId(modifypost.getId());

        return cmdPostRepository.save(originPost);

    }

    @Override
    @Transactional
    public CmdPost deletePost(int postId) {

        CmdPost deletePost = cmdPostRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        deletePost.setDeletedAt(LocalDateTime.now());

        return cmdPostRepository.save(deletePost);
    }

    @Override
    @Transactional
    public CmdLike likePost(CmdLikeDTO like) {

        try {
            CmdLike likePost = cmdLikeRepository.findByEmployeeIdAndPostId(like.getEmployeeId(), like.getPostId());

            if (likePost != null) { //unlike
                cmdLikeRepository.deleteById(likePost.getId());
                return likePost;
            }
        } catch (Exception e) {

        }

        // like
        CmdLike likePost = new CmdLike(like.getEmployeeId(), like.getPostId());
        return cmdLikeRepository.save(likePost);
    }

    @Override
    @Transactional
    public CmdFavorites favoritePost(CmdFavoritesDTO favorite) {
        try {
            CmdFavorites favoritePost = cmdFavoritesRepository.findByEmployeeIdAndPostId(favorite.getEmployeeId(), favorite.getPostId());
            if (favoritePost != null) {  // unfavorite
                cmdFavoritesRepository.deleteById(favoritePost.getId());
                return favoritePost;
            }
        } catch (Exception e) {
        }

        // favorite
        CmdFavorites favoritePost = new CmdFavorites(favorite.getEmployeeId(), favorite.getPostId());
        return cmdFavoritesRepository.save(favoritePost);
    }

}

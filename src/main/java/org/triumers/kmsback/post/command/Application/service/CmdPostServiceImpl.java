package org.triumers.kmsback.post.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.employee.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.domain.aggregate.entity.*;
import org.triumers.kmsback.post.command.domain.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final CmdPostRepository cmdPostRepository;
    private final CmdTagRepository cmdTagRepository;
    private final CmdPostTagRepository cmdPostTagRepository;
    private final CmdLikeRepository cmdLikeRepository;
    private final CmdFavoritesRepository cmdFavoritesRepository;
    private final CmdEmployeeService cmdEmployeeService;

//    private final NotificationService notificationService;

    @Autowired
    public CmdPostServiceImpl(CmdPostRepository cmdPostRepository,
                              CmdTagRepository cmdTagRepository, CmdPostTagRepository cmdPostTagRepository, CmdLikeRepository cmdLikeRepository, CmdFavoritesRepository cmdFavoritesRepository, CmdEmployeeService cmdEmployeeService) {
        this.cmdPostRepository = cmdPostRepository;
        this.cmdTagRepository = cmdTagRepository;
        this.cmdPostTagRepository = cmdPostTagRepository;
        this.cmdLikeRepository = cmdLikeRepository;
        this.cmdFavoritesRepository = cmdFavoritesRepository;
        this.cmdEmployeeService = cmdEmployeeService;
    }

    @Override
    public CmdPostAndTagsDTO registPost(CmdPostAndTagsDTO post) {

        CmdPost registPost = new CmdPost(post.getTitle(), post.getContent(), post.getCreatedAt(),
                post.getAuthorId(), post.getOriginId(), post.getTabRelationId(), post.getCategoryId());
        cmdPostRepository.save(registPost);

        List<CmdTag> tags = registTag(post.getTags(), registPost.getId());

        post.setId(registPost.getId());
        post.setTags(convertTagToTagDTO(tags));

        return post;
    }

    public List<CmdTag> registTag(List<CmdTagDTO> tags, int postId) {

        List<CmdTag> tagList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            CmdTagDTO tagDTO = tags.get(i);

            CmdTag tag = new CmdTag(tagDTO.getId(), tagDTO.getName());
            cmdTagRepository.save(tag);
            tagList.add(tag);

            CmdPostTag postTag = new CmdPostTag(tag.getId(), postId);
            cmdPostTagRepository.save(postTag);
        }

        return tagList;
    }

    @Override
    public CmdPostAndTagsDTO modifyPost(CmdPostAndTagsDTO post) {

        CmdPostAndTagsDTO modifypost = registPost(post);

        CmdPost originPost = cmdPostRepository.findById(modifypost.getOriginId()).orElseThrow(IllegalArgumentException::new);
        originPost.setRecentId(modifypost.getId());
        cmdPostRepository.save(originPost);

        // 추후 yml 수정 후 주석 제거
//        try {
//            CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(originPost.getAuthorId());
//            notificationService.sendMailMime(employeeDTO, originPost);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }

        return modifypost;

    }

    @Override
    public CmdPostAndTagsDTO deletePost(int postId) {

        CmdPost deletePost = cmdPostRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        deletePost.setDeletedAt(LocalDateTime.now());
        cmdPostRepository.save(deletePost);

        CmdPostAndTagsDTO deletedPost = new CmdPostAndTagsDTO(deletePost.getId(), deletePost.getTitle(), deletePost.getContent(),
                deletePost.getCreatedAt(), deletePost.getDeletedAt(), deletePost.getAuthorId(),
                deletePost.getOriginId(), deletePost.getRecentId(), deletePost.getTabRelationId(), deletePost.getCategoryId());

        return deletedPost;
    }

    @Override
    public CmdLikeDTO likePost(CmdLikeDTO like) {

        try {
            CmdLike likePost = cmdLikeRepository.findByEmployeeIdAndPostId(like.getEmployeeId(), like.getPostId());

            if (likePost != null) { //unlike
                cmdLikeRepository.deleteById(likePost.getId());

                CmdLikeDTO likeDTO = new CmdLikeDTO(likePost.getId(), likePost.getEmployeeId(), likePost.getPostId());
                return likeDTO;
            }
        } catch (Exception e) {

        }

        // like
        CmdLike likePost = new CmdLike(like.getEmployeeId(), like.getPostId());
        cmdLikeRepository.save(likePost);

        CmdLikeDTO likeDTO = new CmdLikeDTO(likePost.getId(), likePost.getEmployeeId(), likePost.getPostId());
        return likeDTO;
    }

    @Override
    public CmdFavoritesDTO favoritePost(CmdFavoritesDTO favorite) {
        try {
            CmdFavorites favoritePost = cmdFavoritesRepository.findByEmployeeIdAndPostId(favorite.getEmployeeId(), favorite.getPostId());
            if (favoritePost != null) {  // unfavorite
                cmdFavoritesRepository.deleteById(favoritePost.getId());
                CmdFavoritesDTO favoritesDTO = new CmdFavoritesDTO(favoritePost.getId(), favoritePost.getEmployeeId(), favoritePost.getPostId());
                return favoritesDTO;
            }
        } catch (Exception e) {
        }

        // favorite
        CmdFavorites favoritePost = new CmdFavorites(favorite.getEmployeeId(), favorite.getPostId());
        cmdFavoritesRepository.save(favoritePost);

        CmdFavoritesDTO favoritesDTO = new CmdFavoritesDTO(favoritePost.getId(), favoritePost.getEmployeeId(), favoritePost.getPostId());
        return favoritesDTO;
    }

    private List<CmdTagDTO> convertTagToTagDTO(List<CmdTag> tagList){

        List<CmdTagDTO> tagDTOList = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            CmdTag tag = tagList.get(i);
            CmdTagDTO tagDTO = new CmdTagDTO(tag.getId(), tag.getName());
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }

}

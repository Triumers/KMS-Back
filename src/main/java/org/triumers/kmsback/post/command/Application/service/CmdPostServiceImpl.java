package org.triumers.kmsback.post.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.triumers.kmsback.auth.command.Application.service.AuthService;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;
import org.triumers.kmsback.common.exception.AwsS3Exception;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.s3.service.AwsS3Service;
import org.triumers.kmsback.employee.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.domain.aggregate.entity.*;
import org.triumers.kmsback.post.command.domain.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole.ROLE_ADMIN;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final CmdPostRepository cmdPostRepository;
    private final CmdTagRepository cmdTagRepository;
    private final CmdPostTagRepository cmdPostTagRepository;
    private final CmdLikeRepository cmdLikeRepository;
    private final CmdFavoritesRepository cmdFavoritesRepository;

    private final AwsS3Service awsS3Service;
    private final CmdEmployeeService cmdEmployeeService;
    private final AuthService authService;

//    private final NotificationService notificationService;

    @Autowired
    public CmdPostServiceImpl(CmdPostRepository cmdPostRepository,
                              CmdTagRepository cmdTagRepository, CmdPostTagRepository cmdPostTagRepository, CmdLikeRepository cmdLikeRepository, CmdFavoritesRepository cmdFavoritesRepository, AwsS3Service awsS3Service, CmdEmployeeService cmdEmployeeService, AuthService authService) {
        this.cmdPostRepository = cmdPostRepository;
        this.cmdTagRepository = cmdTagRepository;
        this.cmdPostTagRepository = cmdPostTagRepository;
        this.cmdLikeRepository = cmdLikeRepository;
        this.cmdFavoritesRepository = cmdFavoritesRepository;
        this.awsS3Service = awsS3Service;
        this.cmdEmployeeService = cmdEmployeeService;
        this.authService = authService;
    }

    @Override
    public CmdPostAndTagsDTO registPost(CmdPostAndTagsDTO post) throws NotLoginException {

        Auth employee = authService.whoAmI();
        CmdPost registPost = new CmdPost(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(),
                employee.getId(), post.getOriginId(), post.getTabRelationId(), post.getCategoryId());

        if (post.getId() != null) {
            cmdPostTagRepository.deleteByPostId(post.getId());
        }
        cmdPostRepository.save(registPost);

        registTag(convertStringToTag(post.getTags()), registPost.getId());

        post.setId(registPost.getId());

        return post;
    }

    public List<CmdTag> registTag(List<CmdTagDTO> tags, int postId) {

        List<CmdTag> tagList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            CmdTagDTO tagDTO = tags.get(i);

            CmdTag tag = cmdTagRepository.getByName(tagDTO.getName());
            if (tag == null) {
                tag = new CmdTag(tagDTO.getName());
                cmdTagRepository.save(tag);
            }
            tagList.add(tag);

            CmdPostTag postTag = new CmdPostTag(tag.getId(), postId);
            cmdPostTagRepository.save(postTag);
        }

        return tagList;
    }

    @Override
    public CmdPostAndTagsDTO modifyPost(CmdPostAndTagsDTO post) throws NotLoginException {

        CmdPostAndTagsDTO modifypost = registPost(post);

        CmdPost originPost = cmdPostRepository.findById(modifypost.getOriginId()).orElseThrow(IllegalArgumentException::new);
        originPost.setRecentId(modifypost.getId());
        originPost.setIsEditing(false);
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
    public CmdPostAndTagsDTO deletePost(int postId) throws NotLoginException, NotAuthorizedException {

        if(!isAuthorizedToPost(postId))
            throw new NotAuthorizedException();

        CmdPost deletePost = cmdPostRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        deletePost.setDeletedAt(LocalDateTime.now());
        cmdPostRepository.save(deletePost);

        CmdPostAndTagsDTO deletedPost = new CmdPostAndTagsDTO(deletePost.getId(), deletePost.getTitle(), deletePost.getContent(),
                deletePost.getCreatedAt(), deletePost.getDeletedAt(), deletePost.getAuthorId(),
                deletePost.getOriginId(), deletePost.getRecentId(), deletePost.getTabRelationId(), deletePost.getCategoryId());

        return deletedPost;
    }

    @Override
    public CmdLikeDTO likePost(CmdLikeDTO like) throws NotLoginException {

        Auth employee = authService.whoAmI();
        try {
            CmdLike likePost = cmdLikeRepository.findByEmployeeIdAndPostId(employee.getId(), like.getPostId());

            if (likePost != null) { //unlike
                cmdLikeRepository.deleteById(likePost.getId());

                CmdLikeDTO likeDTO = new CmdLikeDTO(likePost.getId(), likePost.getEmployeeId(), likePost.getPostId());
                return likeDTO;
            }
        } catch (Exception e) {

        }

        // like
        CmdLike likePost = new CmdLike(employee.getId(), like.getPostId());
        cmdLikeRepository.save(likePost);

        CmdLikeDTO likeDTO = new CmdLikeDTO(likePost.getId(), likePost.getEmployeeId(), likePost.getPostId());
        return likeDTO;
    }

    @Override
    public CmdFavoritesDTO favoritePost(CmdFavoritesDTO favorite) throws NotLoginException {

        Auth employee = authService.whoAmI();
        try {
            CmdFavorites favoritePost = cmdFavoritesRepository.findByEmployeeIdAndPostId(employee.getId(), favorite.getPostId());
            if (favoritePost != null) {  // unfavorite
                cmdFavoritesRepository.deleteById(favoritePost.getId());
                CmdFavoritesDTO favoritesDTO = new CmdFavoritesDTO(favoritePost.getId(), favoritePost.getEmployeeId(), favoritePost.getPostId());
                return favoritesDTO;
            }
        } catch (Exception e) {
        }

        // favorite
        CmdFavorites favoritePost = new CmdFavorites(employee.getId(), favorite.getPostId());
        cmdFavoritesRepository.save(favoritePost);

        CmdFavoritesDTO favoritesDTO = new CmdFavoritesDTO(favoritePost.getId(), favoritePost.getEmployeeId(), favoritePost.getPostId());
        return favoritesDTO;
    }

    @Override
    public Boolean isAuthorizedToPost(int originId) throws NotLoginException {

        Auth employee = authService.whoAmI();
        int authorId = cmdPostRepository.findAuthorIdById(originId);

        if (employee.getId() == authorId || employee.getUserRole() == ROLE_ADMIN) {
            return true;
        }
        return false;
    }

    @Override
    public String uploadFile(MultipartFile file) throws AwsS3Exception {
        return awsS3Service.upload(file);
    }

    @Override
    public void changeEditing(int id) {
        CmdPost post = cmdPostRepository.findById(id).orElseThrow(IllegalAccessError::new);
        post.setIsEditing(!post.getIsEditing());
        cmdPostRepository.save(post);
    }

    private List<CmdTagDTO> convertStringToTag(List<String> tags) {
        List<CmdTagDTO> tagList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tagList.add(new CmdTagDTO(tags.get(i)));
        }
        return tagList;
    }
}

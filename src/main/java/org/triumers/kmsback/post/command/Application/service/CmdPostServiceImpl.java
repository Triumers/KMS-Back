package org.triumers.kmsback.post.command.Application.service;

import jakarta.mail.MessagingException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import org.triumers.kmsback.common.ai.dto.ChatGPTResponseDTO;
import org.triumers.kmsback.common.ai.dto.Message;
import org.triumers.kmsback.common.ai.service.OpenAIService;
import org.triumers.kmsback.common.exception.AwsS3Exception;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.s3.service.AwsS3Service;

import org.triumers.kmsback.post.command.domain.aggregate.vo.CmdRequestPostAI;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.domain.aggregate.entity.*;
import org.triumers.kmsback.post.command.domain.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole.ROLE_ADMIN;

@Service
public class CmdPostServiceImpl implements CmdPostService {

    private final CmdPostRepository cmdPostRepository;
    private final CmdTagRepository cmdTagRepository;
    private final CmdPostTagRepository cmdPostTagRepository;
    private final CmdLikeRepository cmdLikeRepository;
    private final CmdFavoritesRepository cmdFavoritesRepository;

    private final AwsS3Service awsS3Service;
    private final AuthService authService;
    private final CmdEmployeeService cmdEmployeeService;

    private final NotificationService notificationService;
    private final OpenAIService openAIService;

    @Autowired
    public CmdPostServiceImpl(CmdPostRepository cmdPostRepository, CmdTagRepository cmdTagRepository,
                              CmdPostTagRepository cmdPostTagRepository, CmdLikeRepository cmdLikeRepository,
                              CmdFavoritesRepository cmdFavoritesRepository, AwsS3Service awsS3Service,
                              AuthService authService, CmdEmployeeService cmdEmployeeService,
                              NotificationService notificationService, OpenAIService openAIService) {
        this.cmdPostRepository = cmdPostRepository;
        this.cmdTagRepository = cmdTagRepository;
        this.cmdPostTagRepository = cmdPostTagRepository;
        this.cmdLikeRepository = cmdLikeRepository;
        this.cmdFavoritesRepository = cmdFavoritesRepository;
        this.awsS3Service = awsS3Service;
        this.authService = authService;
        this.cmdEmployeeService = cmdEmployeeService;
        this.notificationService = notificationService;
        this.openAIService = openAIService;
    }

    @Override
    public CmdPostAndTagsDTO registPost(CmdPostAndTagsDTO post) throws NotLoginException {

        Employee employee = authService.whoAmI();
        CmdPost registPost = new CmdPost(post.getId(), post.getTitle(), sanitizeHTML(post.getContent()), post.getPostImg(),
                LocalDateTime.now(), employee.getId(), post.getOriginId(), post.getTabRelationId(), post.getCategoryId());

        if (post.getId() != null) {
            cmdPostTagRepository.deleteByPostId(post.getId());
        }
        cmdPostRepository.save(registPost);

        registTag(convertStringToTag(post.getTags()), registPost.getId());

        post.setId(registPost.getId());

        return post;
    }

    public static String sanitizeHTML(String html) {

        Whitelist whitelist = Whitelist.relaxed();
        // 허용되지 않을 태그와 속성 추가
        whitelist.removeTags("script", "style", "head", "header", "foot", "footer");
        whitelist.removeAttributes( "style","onclick");

        String cleanedHTML = Jsoup.clean(html, whitelist);
        return cleanedHTML;
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

        // notification
        try {
            CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(originPost.getAuthorId());
            notificationService.sendMailMime(employeeDTO, originPost);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return modifypost;

    }

    @Override
    public CmdPostAndTagsDTO deletePost(int postId) throws NotLoginException, NotAuthorizedException {

        if(!isAuthorizedToPost(postId))
            throw new NotAuthorizedException();

        CmdPost deletePost = cmdPostRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        deletePost.setDeletedAt(LocalDateTime.now());
        cmdPostRepository.save(deletePost);

        CmdPostAndTagsDTO deletedPost = new CmdPostAndTagsDTO(deletePost.getId(), deletePost.getTitle(),
                deletePost.getContent(), deletePost.getPostImg(), deletePost.getCreatedAt(), deletePost.getDeletedAt(),
                deletePost.getAuthorId(), deletePost.getOriginId(), deletePost.getRecentId(),
                deletePost.getTabRelationId(), deletePost.getCategoryId());

        return deletedPost;
    }

    @Override

    public Boolean isAuthorizedToPost(int originId) throws NotLoginException {

        Employee employee = authService.whoAmI();
        int authorId = cmdPostRepository.findAuthorIdById(originId);

        return (employee.getId() == authorId || employee.getUserRole() == ROLE_ADMIN);
    }

    @Override
    public CmdLikeDTO likePost(CmdLikeDTO like) throws NotLoginException {

        Employee employee = authService.whoAmI();
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

        Employee employee = authService.whoAmI();
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

    public String requestToGPT(CmdRequestPostAI request){

        String prompt = getPromptByType(request);
        ChatGPTResponseDTO responseGPT = openAIService.requestToGPT(prompt);

        String responseContent = responseGPT.getChoices().get(0).getMessage().getContent();
        int beginIdx = responseContent.indexOf("{");
        int endIdx = responseContent.indexOf("}");

        return responseContent.substring(beginIdx+1, endIdx).trim();
    }

    String getPromptByType(CmdRequestPostAI request){

        String type = request.getType();
        String content = request.getContent();

        switch (type){
            case "enhancement":
                return contentEnhancement(content);
            case "validation":
                return contentValidation(content);
            case "grammar":
                return grammarCheck(content);
            case "search":
                return search(content);
        }

        return content;
    }

    public String contentEnhancement(String content){
        String prompt =
                " 우리는 이제 글을 업그레이드 할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 업그레이드 해야하는 글이다.\n "+
                        "내용 :{ "+ content + " }"
                        + "\n { } 안에 들어간 내용의 태그 안에 있는 내용들이 맞는지 확인하고" +
                        " 태그 안의 내용을 업그레이드 및 정리한 후에," +
                        " 업그레이드 한 내용을 아래 예시처럼 답변 : {} 안에 담아 반환한다." +
                        "예시\n" +
                        example();
        return prompt;
    }

    public String contentValidation(String content){
        String prompt =
                " 우리는 이제 글에 대한 내용을 검증할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 검증해야 하는 글이다.\n "+
                        "내용 :{ "+ content + " }"
                        + "{ } 안에 들어간 글의 내용들이 지식적으로 맞는지 검증하고 틀린 내용에 대한 의견과 이에 대한 옳은 내용을 정리해서 "
                        + " 아래 예시처럼 답변 : {} 안에 담아 반환한다."
                        + "예시\n" +
                        example();

        return prompt;
    }

    public String grammarCheck(String content){
        String prompt =
                " 우리는 이제 글의 맞춤법을 수정 할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 맞춤법을 수정해야 하는 글이다. "+
                        "내용 :{ "+ content + " }"
                        + "{ } 안에 들어간 글의 내용들의 맞춤법과 문맥을 검사하고 틀린 부분은 옳게 수정한 뒤"
                        + " 아래 예시처럼 답변 : {} 안에 담아 반환한다."
                        + "예시\n" +
                        example();

        return prompt;
    }

    public String search(String content){
        String prompt =
                " 우리는 이제 요청된 질문에 대해서 옳은 답을 할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 답변해야 하는 질문이다. "+
                        "질문 :{ "+ content + " }"
                        + "{ } 안에 들어간 질문에 대한 옳은 답변을 찾고 지식적으로 정리해서"
                        + " 아래 예시처럼 답변 : {} 안에 담아 반환한다."
                        + "예시\n" +
                        example();

        return prompt;
    }

    String example(){
        return "답변 : { " +
                " 요청에 대한 값" +
                " }";
    }
}

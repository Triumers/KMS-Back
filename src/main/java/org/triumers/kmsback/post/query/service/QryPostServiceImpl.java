package org.triumers.kmsback.post.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.group.query.service.QryGroupService;
import org.triumers.kmsback.tab.command.Application.service.CmdTabService;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.repository.QryPostMapper;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.service.QryEmployeeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryPostServiceImpl implements QryPostService {

    private final QryPostMapper qryPostMapper;
    private final QryEmployeeService qryEmployeeService;
    private final AuthService authService;
    private final QryGroupService qryGroupService;

    public QryPostServiceImpl(QryPostMapper qryPostMapper, QryEmployeeService qryEmployeeService, CmdTabService cmdTabService, AuthService authService, QryGroupService qryGroupService) {
        this.qryPostMapper = qryPostMapper;
        this.qryEmployeeService = qryEmployeeService;
        this.authService = authService;
        this.qryGroupService = qryGroupService;
    }

    @Override
    public Page<QryPostAndTagsDTO> findPostListByTab(QryRequestPost request, Pageable pageable) throws NotLoginException, WrongInputValueException {

        List<QryPostAndTag> postList = qryPostMapper.selectTabPostList(request, pageable);
        for (int i = 0; i < postList.size(); i++) {
            List<QryTag> tagList = qryPostMapper.selectTagList(postList.get(i).getId());
            postList.get(i).setTags(tagList);
        }

        List<QryPostAndTagsDTO> postDTOList = QryPostAndTagListToDTOList(postList, "origin");
        
        long total = qryPostMapper.selectPostCount(request, null);

        return new PageImpl<>(postDTOList, pageable, total);
    }

    @Override
    public Page<QryPostAndTagsDTO> findAllPostListByEmployee(QryRequestPost request, Pageable pageable) throws NotLoginException, WrongInputValueException {

        Employee employee = authService.whoAmI();

        List<Integer> tabList = qryGroupService.findGroupIdByEmployeeId(employee.getId());

        request.setTabRelationId(null);
        request.setTabList(tabList);

        return findPostListByTab(request, pageable);
    }

    @Override
    public QryPostAndTagsDTO findPostById(int postId) throws NotLoginException, WrongInputValueException {
        QryPostAndTag post = qryPostMapper.selectPostById(postId);

        QryEmployeeDTO employeeDTO = qryEmployeeService.findByIdIncludeEnd(post.getAuthorId());

        QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                post.getPostImg(), post.getCreatedAt(), employeeDTO, post.getOriginId(),
                post.getRecentId(), post.getTabRelationId(), post.getCategoryId());

        postDTO.setTags(convertTagToString(post.getTags()));
        postDTO.setHistory(findHistoryListByOriginId(postId));
        postDTO.setParticipants(findParticipantsListByOriginId(postId));
        postDTO.setLikeList(findLikeListByPostId(postId));
        postDTO.setIsLike(findIsLikedByPostId(postId));
        postDTO.setIsFavorite(findIsFavoriteByPostId(postId));

        return postDTO;
    }

    private List<QryEmployeeDTO> findParticipantsListByOriginId(int postId) throws WrongInputValueException {

        List<QryEmployeeDTO> participantList = new ArrayList<>();
        List<Integer> employeeList = qryPostMapper.selectParticipantsListByOriginId(postId);
        for (int i = 0; i < employeeList.size(); i++) {
            QryEmployeeDTO employee = qryEmployeeService.findByIdIncludeEnd(employeeList.get(i));
            participantList.add(employee);
        }
        return participantList;
    }

    @Override
    public List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId) throws NotLoginException, WrongInputValueException {
        List<QryPostAndTag> historyList = qryPostMapper.selectHistoryListByOriginId(originId);

        return QryPostAndTagListToDTOList(historyList, "history");
    }

    @Override
    public List<QryEmployeeDTO> findLikeListByPostId(int postId) throws WrongInputValueException {

        List<QryLike> likeList = qryPostMapper.selectLikeListByPostId(postId);
        List<QryEmployeeDTO> likeMemberList = new ArrayList<>();

        if (likeList != null) {
            for (int i = 0; i < likeList.size(); i++) {
                int memberId = likeList.get(i).getEmployeeId();

                QryEmployeeDTO employeeDTO = qryEmployeeService.findByIdIncludeEnd(memberId);
                likeMemberList.add(employeeDTO);
            }
        }

        return likeMemberList;
    }

    @Override
    public Boolean getIsEditingById(int postId) {

        return qryPostMapper.selectIsEditingByPostId(postId);
    }

    @Override
    public List<QryPostAndTagsDTO> findPostByEmployeeId(int employeeId) {

        List<QryPostAndTag> myPostList = qryPostMapper.selectMyPostList(employeeId);

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < myPostList.size(); i++) {
            QryPostAndTag myPost = myPostList.get(i);
            QryPostAndTagsDTO myPostDTO = new QryPostAndTagsDTO(myPost.getId(), myPost.getTitle(), myPost.getOriginId());

            postDTOList.add(myPostDTO);
        }

        return postDTOList;
    }

    @Override
    public List<QryPostAndTagsDTO> findLikePostByEmployeeId(int employeeId) {

        List<QryPostAndTag> myLikeList = qryPostMapper.selectMyLikeList(employeeId);

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < myLikeList.size(); i++) {
            QryPostAndTag myPost = myLikeList.get(i);
            QryPostAndTagsDTO myPostDTO = new QryPostAndTagsDTO(myPost.getId(), myPost.getTitle(), myPost.getOriginId());

            postDTOList.add(myPostDTO);
        }

        return postDTOList;
    }

    @Override
    public List<QryPostAndTagsDTO> findFavoritePostByEmployeeId(int employeeId) {
        List<QryPostAndTag> myLikeList = qryPostMapper.selectMyFavoriteList(employeeId);

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < myLikeList.size(); i++) {
            QryPostAndTag myPost = myLikeList.get(i);
            QryPostAndTagsDTO myPostDTO = new QryPostAndTagsDTO(myPost.getId(), myPost.getTitle(), myPost.getOriginId());

            postDTOList.add(myPostDTO);
        }

        return postDTOList;
    }

    @Override
    public Boolean findIsLikedByPostId(int postId) throws NotLoginException {

        Employee employee = authService.whoAmI();

        return qryPostMapper.selectIsLikedByPostId(postId, employee.getId()) > 0;
    }

    @Override
    public Boolean findIsFavoriteByPostId(int postId) throws NotLoginException {

        Employee employee = authService.whoAmI();

        return qryPostMapper.selectIsFavoriteByPostId(postId, employee.getId()) > 0;
    }


    private List<QryPostAndTagsDTO> QryPostAndTagListToDTOList(List<QryPostAndTag> postList, String type) throws NotLoginException, WrongInputValueException {

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            QryPostAndTag post = postList.get(i);

            int authorId = post.getAuthorId();
            int originId = (post.getOriginId() != null) ? post.getOriginId() : post.getId();
            if(type.equals("origin")){
                authorId = qryPostMapper.originAuthorId(originId);
            }

            QryEmployeeDTO employeeDTO = qryEmployeeService.findByIdIncludeEnd(authorId);

            QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                    post.getPostImg(), post.getCreatedAt(), employeeDTO, post.getOriginId(),
                    post.getRecentId(), post.getTabRelationId(), post.getCategoryId());
            postDTO.setTags(convertTagToString(post.getTags()));

            postDTO.setIsLike(findIsLikedByPostId(originId));
            postDTO.setIsFavorite(findIsFavoriteByPostId(originId));

            if(type.equals("origin")){
                List<QryEmployeeDTO> like = findLikeListByPostId(originId);
                postDTO.setLikeList(like);
            }
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    private List<String> convertTagToString(List<QryTag> tags) {
        List<String> tagList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tagList.add(tags.get(i).getName());
        }
        return tagList;
    }
}

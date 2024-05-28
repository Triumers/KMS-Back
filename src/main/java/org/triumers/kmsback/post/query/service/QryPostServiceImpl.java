package org.triumers.kmsback.post.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.user.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.repository.QryPostMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryPostServiceImpl implements QryPostService {

    private final QryPostMapper qryPostMapper;
    private final CmdEmployeeService cmdEmployeeService;

    public QryPostServiceImpl(QryPostMapper qryPostMapper, CmdEmployeeService cmdEmployeeService) {
        this.qryPostMapper = qryPostMapper;
        this.cmdEmployeeService = cmdEmployeeService;
    }

    @Override
    public Page<QryPostAndTagsDTO> findPostListByTab(QryRequestPost request, Pageable pageable) {

        List<QryPostAndTag> postList = qryPostMapper.selectTabPostList(request, pageable);
        for (int i = 0; i < postList.size(); i++) {
            List<QryTag> tagList = qryPostMapper.selectTagList(postList.get(i).getId());
            postList.get(i).setTags(tagList);
        }

        List<QryPostAndTagsDTO> postDTOList = QryPostAndTagListToDTOList(postList, "origin");

        long total = qryPostMapper.countTabPostList(request.getTabRelationId());

        return new PageImpl<>(postDTOList, pageable, total);
    }

    @Override
    public QryPostAndTagsDTO findPostById(int postId) {
        QryPostAndTag post = qryPostMapper.selectPostById(postId);

        CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(post.getAuthorId());

        QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                post.getPostImg(), post.getCreatedAt(), employeeDTO, post.getOriginId(),
                post.getRecentId(), post.getTabRelationId(), post.getCategoryId());

        postDTO.setTags(convertTagToString(post.getTags()));
        postDTO.setHistory(findHistoryListByOriginId(postId));
        postDTO.setParticipants(findParticipantsListByOriginId(postId));

        return postDTO;
    }

    private List<CmdEmployeeDTO> findParticipantsListByOriginId(int postId) {

        List<CmdEmployeeDTO> participantList = new ArrayList<>();
        List<Integer> employeeList = qryPostMapper.selectParticipantsListByOriginId(postId);
        for (int i = 0; i < employeeList.size(); i++) {
            CmdEmployeeDTO employee = cmdEmployeeService.findEmployeeById(employeeList.get(i));
            participantList.add(employee);
        }
        return participantList;
    }

    @Override
    public List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId) {
        List<QryPostAndTag> historyList = qryPostMapper.selectHistoryListByOriginId(originId);

        return QryPostAndTagListToDTOList(historyList, "history");
    }

    @Override
    public List<CmdEmployeeDTO> findLikeListByPostId(int postId) {

        List<QryLike> likeList = qryPostMapper.selectLikeListByPostId(postId);

        List<CmdEmployeeDTO> likeMemberList = new ArrayList<>();
        for (int i = 0; i < likeList.size(); i++) {
            int memberId = likeList.get(i).getEmployeeId();
            CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(memberId);
            likeMemberList.add(employeeDTO);
        }
        return likeMemberList;
    }

    @Override
    public Boolean getIsEditingById(int postId) {

        return qryPostMapper.selectIsEditingByPostId(postId);
    }

    private List<QryPostAndTagsDTO> QryPostAndTagListToDTOList(List<QryPostAndTag> postList, String type){

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            QryPostAndTag post = postList.get(i);

            int authorId = post.getAuthorId();
            if(type.equals("origin")){
                int originId = (post.getOriginId() != null) ? post.getOriginId() : post.getId();
                authorId = qryPostMapper.originAuthorId(originId);
            }

            CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(authorId);
            QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                    post.getPostImg(), post.getCreatedAt(), employeeDTO, post.getOriginId(),
                    post.getRecentId(), post.getTabRelationId(), post.getCategoryId());
            postDTO.setTags(convertTagToString(post.getTags()));
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

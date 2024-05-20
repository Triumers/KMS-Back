package org.triumers.kmsback.post.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.employee.command.Application.service.CmdEmployeeService;
import org.triumers.kmsback.employee.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.employee.query.service.QryEmployeeService;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;
import org.triumers.kmsback.post.query.dto.QryLikeDTO;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.dto.QryPostDTO;
import org.triumers.kmsback.post.query.dto.QryTagDTO;
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
    public Page<QryPostAndTagsDTO> findPostListByTab(int tabId, Pageable pageable) {

        List<QryPostAndTag> postList = qryPostMapper.selectTabPostList(tabId, pageable);
        for (int i = 0; i < postList.size(); i++) {
            List<QryTag> tagList = qryPostMapper.selectTagList(postList.get(i).getId());
            postList.get(i).setTags(tagList);
        }

        List<QryPostAndTagsDTO> postDTOList = QryPostAndTagListToDTOList(postList);

        long total = qryPostMapper.countTabPostList(tabId);

        return new PageImpl<>(postDTOList, pageable, total);
    }

    @Override
    public QryPostAndTagsDTO findPostById(int postId) {
        QryPostAndTag post = qryPostMapper.selectPostById(postId);

        CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(post.getAuthorId());

        QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                post.getCreatedAt(), employeeDTO, post.getOriginId(),
                post.getRecentId(), post.getTabRelationId());

        postDTO.setTags(convertTagToTagDTO(post.getTags()));
        postDTO.setHistory(findHistoryListByOriginId(postId));

        return postDTO;
    }

    @Override
    public List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId) {
        List<QryPostAndTag> historyList = qryPostMapper.selectHistoryListByOriginId(originId);

        return QryPostAndTagListToDTOList(historyList);
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

    private List<QryPostAndTagsDTO> QryPostAndTagListToDTOList(List<QryPostAndTag> postList){

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            QryPostAndTag post = postList.get(i);
            CmdEmployeeDTO employeeDTO = cmdEmployeeService.findEmployeeById(post.getAuthorId());
            QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                    post.getCreatedAt(), employeeDTO, post.getOriginId(),
                    post.getRecentId(), post.getTabRelationId());
            postDTO.setTags(convertTagToTagDTO(post.getTags()));
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    private List<QryTagDTO> convertTagToTagDTO(List<QryTag> tagList){

        List<QryTagDTO> tagDTOList = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            QryTag tag = tagList.get(i);
            QryTagDTO tagDTO = new QryTagDTO(tag.getId(), tag.getName());
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }
}

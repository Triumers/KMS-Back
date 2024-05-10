package org.triumers.kmsback.post.query.service;

import org.springframework.stereotype.Service;
import org.triumers.kmsback.employee.query.service.QryEmployeeService;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.dto.QryPostDTO;
import org.triumers.kmsback.post.query.dto.QryTagDTO;
import org.triumers.kmsback.post.query.repository.QryPostMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryPostServiceImpl implements QryPostService {

    private final QryPostMapper qryPostMapper;

    public QryPostServiceImpl(QryPostMapper qryPostMapper) {
        this.qryPostMapper = qryPostMapper;
    }

    @Override
    public List<QryPostAndTagsDTO> findPostListByTab(int tabId) {

        List<QryPostAndTag> postList = qryPostMapper.selectTabPostList(tabId);

        return QryPostAndTagListToDTOList(postList);
    }

    @Override
    public QryPostAndTagsDTO findPostById(int postId) {
        QryPostAndTag post = qryPostMapper.selectPostById(postId);

        QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                post.getCreatedAt(), post.getAuthorId(), post.getOriginId(),
                post.getRecentId(), post.getTabRelationId());

        postDTO.setTags(convertTagToTagDTO(post.getTags()));

        return postDTO;
    }

    @Override
    public List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId) {
        List<QryPostAndTag> historyList = qryPostMapper.selectHistoryListByOriginId(originId);

        return QryPostAndTagListToDTOList(historyList);
    }

    private List<QryPostAndTagsDTO> QryPostAndTagListToDTOList(List<QryPostAndTag> postList){

        List<QryPostAndTagsDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            QryPostAndTag post = postList.get(i);
            QryPostAndTagsDTO postDTO = new QryPostAndTagsDTO(post.getId(), post.getTitle(), post.getContent(),
                    post.getCreatedAt(), post.getAuthorId(), post.getOriginId(),
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

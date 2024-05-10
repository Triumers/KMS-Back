package org.triumers.kmsback.post.query.service;

import org.springframework.stereotype.Service;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.repository.QryPostMapper;

import java.util.List;

@Service
public class QryPostServiceImpl implements QryPostService {

    private final QryPostMapper qryPostMapper;

    public QryPostServiceImpl(QryPostMapper qryPostMapper) {
        this.qryPostMapper = qryPostMapper;
    }

    @Override
    public List<QryPostAndTag> getPostListByTab(int tabId) {
        List<QryPostAndTag> postList = qryPostMapper.selectTabPostList(tabId);
        return postList;
    }
}

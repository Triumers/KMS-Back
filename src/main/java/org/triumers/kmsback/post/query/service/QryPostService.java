package org.triumers.kmsback.post.query.service;

import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;

import java.util.List;

public interface QryPostService {

    List<QryPostAndTag> getPostListByTab(int tabId);
}

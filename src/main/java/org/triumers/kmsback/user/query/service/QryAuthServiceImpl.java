package org.triumers.kmsback.user.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.service.QryPostService;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QryAuthServiceImpl implements QryAuthService {

    private final AuthService authService;
    private final QryPostService qryPostService;

    @Autowired
    public QryAuthServiceImpl(AuthService authService, QryPostService qryPostService) {
        this.authService = authService;
        this.qryPostService = qryPostService;
    }

    @Override
    public QryDocsDTO findMyPost() throws NotLoginException {

        QryDocsDTO result = new QryDocsDTO();
        result.setDocsType("POST");
        List<Map<String, String>> myPostListDTO = new ArrayList<>();

        List<QryPostAndTagsDTO> myPostList = qryPostService.findPostByEmployeeId(getLoggedInEmployeeId());

        for (QryPostAndTagsDTO post : myPostList) {
            Map<String, String> postMap = new HashMap<>();
            postMap.put("id", String.valueOf(post.getId()));
            postMap.put("title", post.getTitle());
            myPostListDTO.add(postMap);
        }
        result.setDocsInfoList(myPostListDTO);

        return result;
    }

    @Override
    public QryDocsDTO findMyComment() {
        return null;
    }

    @Override
    public QryDocsDTO findMyLike() {
        return null;
    }

    @Override
    public QryDocsDTO findMyBookmark() {
        return null;
    }

    private int getLoggedInEmployeeId() throws NotLoginException {
        return authService.whoAmI().getId();
    }
}

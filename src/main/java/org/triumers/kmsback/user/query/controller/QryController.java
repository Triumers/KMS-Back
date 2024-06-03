package org.triumers.kmsback.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponseDocsVO;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;
import org.triumers.kmsback.user.query.service.QryAuthService;

@RestController
@RequestMapping("/my-page")
public class QryController {

    private final QryAuthService qryAuthService;

    @Autowired
    public QryController(QryAuthService qryAuthService) {
        this.qryAuthService = qryAuthService;
    }

    @GetMapping("/my-post")
    public ResponseEntity<QryResponseDocsVO> findMyPost() {

        try {
            QryDocsDTO myPost = qryAuthService.findMyPost();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseDocsVO(
                    "조회성공", myPost.getDocsType(), myPost.getDocsInfoList()));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new QryResponseDocsVO(e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/like-post")
    public ResponseEntity<QryResponseDocsVO> findLikePost() {

        try {
            QryDocsDTO likePost = qryAuthService.findMyLike();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseDocsVO(
                    "조회성공", likePost.getDocsType(), likePost.getDocsInfoList()));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new QryResponseDocsVO(e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/favorite-post")
    public ResponseEntity<QryResponseDocsVO> findFavoritePost() {

        try {
            QryDocsDTO favoritePost = qryAuthService.findMyFavoritePost();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseDocsVO(
                    "조회성공", favoritePost.getDocsType(), favoritePost.getDocsInfoList()));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new QryResponseDocsVO(e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

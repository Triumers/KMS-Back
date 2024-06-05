package org.triumers.kmsback.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponseDocsVO;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponseEmployeeVO;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.service.QryAuthService;

import java.util.List;

@RestController
@RequestMapping("/my-page")
public class QryAuthController {

    private final QryAuthService qryAuthService;

    @Autowired
    public QryAuthController(QryAuthService qryAuthService) {
        this.qryAuthService = qryAuthService;
    }

    @GetMapping
    public ResponseEntity<QryResponseEmployeeVO> myInfo() {

        try {
            QryEmployeeDTO myInfo = qryAuthService.myInfo();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", List.of(myInfo)));
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new QryResponseEmployeeVO(e.getMessage(), null));
        } catch (WrongInputValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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

    @GetMapping("/my-comment")
    public ResponseEntity<QryResponseDocsVO> findMyComment() {

        try {
            QryDocsDTO myComment = qryAuthService.findMyComment();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseDocsVO(
                    "조회성공", myComment.getDocsType(), myComment.getDocsInfoList()));
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

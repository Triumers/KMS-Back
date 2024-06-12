package org.triumers.kmsback.post.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.service.QryPostService;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

@RestController
@RequestMapping("/post")
public class QryPostController {

    private final QryPostService qryPostService;

    @Autowired
    public QryPostController(QryPostService qryPostService) {
        this.qryPostService = qryPostService;
    }

    @PostMapping("/tab")
    public ResponseEntity<Page<QryPostAndTagsDTO>> findPostListByTab(@RequestBody QryRequestPost request) throws NotLoginException, WrongInputValueException {

        try {
            PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
            Page<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(request, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(postList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/tab/all")
    public ResponseEntity<Page<QryPostAndTagsDTO>> findAllPostListByEmployee(@RequestBody QryRequestPost request) throws NotLoginException, WrongInputValueException {

        try {
            PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
            Page<QryPostAndTagsDTO> postList = qryPostService.findAllPostListByEmployee(request, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(postList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<QryPostAndTagsDTO> findPostById(@PathVariable int id) throws NotLoginException, WrongInputValueException {

        try {
            QryPostAndTagsDTO post = qryPostService.findPostById(id);

            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<QryPostAndTagsDTO>> findHistoryListByOriginId(@PathVariable int id) throws NotLoginException, WrongInputValueException {

        try {
            List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(id);

            return ResponseEntity.status(HttpStatus.OK).body(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<List<QryEmployeeDTO>> findLikeListByPostId(@PathVariable int id) throws WrongInputValueException {

        try {
            List<QryEmployeeDTO> likeList = qryPostService.findLikeListByPostId(id);

            return ResponseEntity.status(HttpStatus.OK).body(likeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/like/employee")
    public ResponseEntity<Boolean> findIsLikedByPostId(@PathVariable int id) throws NotLoginException {

        try {
            Boolean isLiked = qryPostService.findIsLikedByPostId(id);

            return ResponseEntity.status(HttpStatus.OK).body(isLiked);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/favorite/employee")
    public ResponseEntity<Boolean> findIsFavoriteByPostId(@PathVariable int id) throws NotLoginException {

        try {
            Boolean isFavorite = qryPostService.findIsFavoriteByPostId(id);

            return ResponseEntity.status(HttpStatus.OK).body(isFavorite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/isEditing")
    public ResponseEntity<Boolean> getEditingState(@PathVariable int id) {

        try {
            Boolean isEditing = qryPostService.getIsEditingById(id);

            return ResponseEntity.status(HttpStatus.OK).body(isEditing);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}

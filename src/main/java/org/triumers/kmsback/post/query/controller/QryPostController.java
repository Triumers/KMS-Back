package org.triumers.kmsback.post.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.service.QryPostService;

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
    public ResponseEntity<Page<QryPostAndTagsDTO>> findPostListByTab(@RequestBody QryRequestPost request) throws NotLoginException {

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(request, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @PostMapping("/tab/all")
    public ResponseEntity<Page<QryPostAndTagsDTO>> findAllPostListByEmployee(@RequestBody QryRequestPost request) throws NotLoginException {

        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<QryPostAndTagsDTO> postList = qryPostService.findAllPostListByEmployee(request, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<QryPostAndTagsDTO> findPostById(@PathVariable int id) throws NotLoginException {
        QryPostAndTagsDTO post = qryPostService.findPostById(id);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<QryPostAndTagsDTO>> findHistoryListByOriginId(@PathVariable int id) throws NotLoginException {
        List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(id);

        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<List<CmdEmployeeDTO>> findLikeListByPostId(@PathVariable int id){
        List<CmdEmployeeDTO> likeList = qryPostService.findLikeListByPostId(id);

        return ResponseEntity.status(HttpStatus.OK).body(likeList);
    }

    @GetMapping("/{id}/like/employee")
    public ResponseEntity<Boolean> findIsLikedByPostId(@PathVariable int id) throws NotLoginException {
        Boolean isLiked = qryPostService.findIsLikedByPostId(id);

        return ResponseEntity.status(HttpStatus.OK).body(isLiked);
    }

    @GetMapping("/{id}/favorite/employee")
    public ResponseEntity<Boolean> findIsFavoriteByPostId(@PathVariable int id) throws NotLoginException {
        Boolean isFavorite = qryPostService.findIsFavoriteByPostId(id);

        return ResponseEntity.status(HttpStatus.OK).body(isFavorite);
    }

    @GetMapping("/{id}/isEditing")
    public ResponseEntity<Boolean> getEditingState(@PathVariable int id){
        Boolean isEditing = qryPostService.getIsEditingById(id);

        return ResponseEntity.status(HttpStatus.OK).body(isEditing);
    }

}

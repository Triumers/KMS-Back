package org.triumers.kmsback.post.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.employee.query.dto.QryEmployeeDTO;
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

    @GetMapping("/tab/{id}")
    public ResponseEntity<List<QryPostAndTagsDTO>> findPostListByTab(@PathVariable int id){
        List<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(id);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QryPostAndTagsDTO> findPostById(@PathVariable int id){
        QryPostAndTagsDTO post = qryPostService.findPostById(id);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<QryPostAndTagsDTO>> findHistoryListByOriginId(@PathVariable int id){
        List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(id);

        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<List<QryEmployeeDTO>> findLikeListByPostId(@PathVariable int id){
        List<QryEmployeeDTO> likeList = qryPostService.findLikeListByPostId(id);

        return ResponseEntity.status(HttpStatus.OK).body(likeList);
    }


}

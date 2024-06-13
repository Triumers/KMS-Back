package org.triumers.kmsback.post.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.triumers.kmsback.common.exception.AwsS3Exception;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.command.domain.aggregate.vo.CmdRequestPostAI;

@RestController
@RequestMapping("/post")
public class CmdPostController {

    private final CmdPostService cmdPostService;

    @Autowired
    public CmdPostController(CmdPostService cmdPostService) {
        this.cmdPostService = cmdPostService;
    }

    @PostMapping("/regist")
    public ResponseEntity<CmdPostAndTagsDTO> registPost(@RequestBody CmdPostAndTagsDTO newPost) throws NotLoginException {

        try {
            if(newPost.getTitle() == null || newPost.getContent() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            CmdPostAndTagsDTO savedPost = cmdPostService.registPost(newPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<CmdPostAndTagsDTO> modifyPost(@RequestBody CmdPostAndTagsDTO modifyPost) throws NotLoginException {

        try {
            if(modifyPost.getTitle() == null || modifyPost.getContent() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            CmdPostAndTagsDTO modifiedPost = cmdPostService.modifyPost(modifyPost);
            return ResponseEntity.status(HttpStatus.OK).body(modifiedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CmdPostAndTagsDTO> deletePost(@PathVariable int id) throws NotLoginException, NotAuthorizedException {

        try {
            CmdPostAndTagsDTO deletedPost = cmdPostService.deletePost(id);

            return ResponseEntity.status(HttpStatus.OK).body(deletedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<CmdLikeDTO> likePost(@RequestBody CmdLikeDTO cmdLikeDTO) throws NotLoginException {

        try {
            if(cmdLikeDTO.getPostId() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            CmdLikeDTO like = cmdPostService.likePost(cmdLikeDTO);
            return ResponseEntity.status(HttpStatus.OK).body(like);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/favorite")
    public ResponseEntity<CmdFavoritesDTO> favoritePost(@RequestBody CmdFavoritesDTO cmdFavoritesDTO) throws NotLoginException {

        try {
            if(cmdFavoritesDTO.getPostId() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            CmdFavoritesDTO favorite = cmdPostService.favoritePost(cmdFavoritesDTO);
            return ResponseEntity.status(HttpStatus.OK).body(favorite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/ai")
    public ResponseEntity<String> requestToAI(@RequestBody CmdRequestPostAI request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cmdPostService.requestToGPT(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/isEditing/{id}")
    public void editingPost(@PathVariable int id){
        try {
            cmdPostService.changeEditing(id);
        } catch (Exception e) {
            System.out.println("[error] " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws AwsS3Exception {
        try {
            return cmdPostService.uploadFile(file);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/isAuthor/{originId}")
    public Boolean isAuthorizedToPost(@PathVariable int originId) throws NotLoginException {
        try {
            return cmdPostService.isAuthorizedToPost(originId);
        } catch (Exception e) {
            return null;
        }
    }
}

package org.triumers.kmsback.post.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;

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

        if(newPost.getTitle() == null || newPost.getContent() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PostMapping("/modify")
    public ResponseEntity<CmdPostAndTagsDTO> modifyPost(@RequestBody CmdPostAndTagsDTO modifyPost) throws NotLoginException {

        if(modifyPost.getTitle() == null || modifyPost.getContent() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdPostAndTagsDTO modifiedPost = cmdPostService.modifyPost(modifyPost);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CmdPostAndTagsDTO> deletePost(@PathVariable int id) throws NotLoginException, NotAuthorizedException {

        CmdPostAndTagsDTO deletedPost = cmdPostService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedPost);
    }

    @PostMapping("/like")
    public ResponseEntity<CmdLikeDTO> deletePost(@RequestBody CmdLikeDTO cmdLikeDTO) throws NotLoginException {

        if(cmdLikeDTO.getPostId() == null || cmdLikeDTO.getEmployeeId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdLikeDTO like = cmdPostService.likePost(cmdLikeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(like);
    }

    @PostMapping("/favorite")
    public ResponseEntity<CmdFavoritesDTO> deletePost(@RequestBody CmdFavoritesDTO cmdFavoritesDTO) throws NotLoginException {

        if(cmdFavoritesDTO.getPostId() == null || cmdFavoritesDTO.getEmployeeId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdFavoritesDTO favorite = cmdPostService.favoritePost(cmdFavoritesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(favorite);
    }

    @PostMapping("/isEditing/{id}")
    public void editingPost(@PathVariable int id){
        cmdPostService.changeEditing(id);
    }

    @GetMapping("/isAuthor/{originId}")
    public Boolean isAuthorizedToPost(@PathVariable int originId) throws NotLoginException {
        return cmdPostService.isAuthorizedToPost(originId);
    }
}

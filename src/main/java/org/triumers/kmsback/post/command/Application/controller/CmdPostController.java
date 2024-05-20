package org.triumers.kmsback.post.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

@RestController
@RequestMapping("/post")
public class CmdPostController {

    private final CmdPostService cmdPostService;

    @Autowired
    public CmdPostController(CmdPostService cmdPostService) {
        this.cmdPostService = cmdPostService;
    }

    @PostMapping("/regist")
    public ResponseEntity<CmdPostAndTagsDTO> registPost(@RequestBody CmdPostAndTagsDTO cmdPostAndTagsDTO){

        if(cmdPostAndTagsDTO.getTitle() == null || cmdPostAndTagsDTO.getContent() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(cmdPostAndTagsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PostMapping("/modify")
    public ResponseEntity<CmdPostAndTagsDTO> modifyPost(@RequestBody CmdPostAndTagsDTO cmdPostAndTagsDTO){

        if(cmdPostAndTagsDTO.getTitle() == null || cmdPostAndTagsDTO.getContent() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdPostAndTagsDTO modifiedPost = cmdPostService.modifyPost(cmdPostAndTagsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CmdPostAndTagsDTO> deletePost(@PathVariable int id){

        CmdPostAndTagsDTO deletedPost = cmdPostService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedPost);
    }

    @PostMapping("like")
    public ResponseEntity<CmdLikeDTO> deletePost(@RequestBody CmdLikeDTO cmdLikeDTO){

        if(cmdLikeDTO.getPostId() == null || cmdLikeDTO.getEmployeeId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdLikeDTO like = cmdPostService.likePost(cmdLikeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(like);
    }

    @PostMapping("favorite")
    public ResponseEntity<CmdFavoritesDTO> deletePost(@RequestBody CmdFavoritesDTO cmdFavoritesDTO){

        if(cmdFavoritesDTO.getPostId() == null || cmdFavoritesDTO.getEmployeeId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdFavoritesDTO favorite = cmdPostService.favoritePost(cmdFavoritesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(favorite);
    }


}

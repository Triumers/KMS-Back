package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdFavorites;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdLike;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

public interface CmdPostService {
    CmdPostAndTagsDTO registPost(CmdPostAndTagsDTO post) throws NotLoginException;

    CmdPostAndTagsDTO modifyPost(CmdPostAndTagsDTO post) throws NotLoginException;

    CmdPostAndTagsDTO deletePost(int postId) throws NotLoginException;

    CmdLikeDTO likePost(CmdLikeDTO like) throws NotLoginException;

    CmdFavoritesDTO favoritePost(CmdFavoritesDTO favorite) throws NotLoginException;

    void changeEditing(int id);

    Boolean isAuthorizedToPost(int originId) throws NotLoginException;
}

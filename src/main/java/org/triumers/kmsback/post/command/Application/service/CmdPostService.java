package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdFavorites;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdLike;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

public interface CmdPostService {
    CmdPostAndTagsDTO registPost(CmdPostAndTagsDTO post);

    CmdPostAndTagsDTO modifyPost(CmdPostAndTagsDTO post);

    CmdPostAndTagsDTO deletePost(int postId);

    CmdLikeDTO likePost(CmdLikeDTO like);

    CmdFavoritesDTO favoritePost(CmdFavoritesDTO favorite);

    void changeEditing(int id);
}

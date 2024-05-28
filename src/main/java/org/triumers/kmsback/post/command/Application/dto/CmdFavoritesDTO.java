package org.triumers.kmsback.post.command.Application.dto;

import lombok.Data;

@Data
public class CmdFavoritesDTO {

    private Integer id;
    private Integer employeeId;
    private Integer postId;

    public CmdFavoritesDTO(Integer id, Integer employeeId, Integer postId) {
        this.id = id;
        this.employeeId = employeeId;
        this.postId = postId;
    }

    public CmdFavoritesDTO(Integer employeeId, Integer postId) {
        this.employeeId = employeeId;
        this.postId = postId;
    }
}

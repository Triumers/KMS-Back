package org.triumers.kmsback.post.command.Application.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CmdLikeDTO {

    private Integer id;
    private Integer employeeId;
    private Integer postId;

    public CmdLikeDTO(Integer id, Integer employeeId, Integer postId) {
        this.id = id;
        this.employeeId = employeeId;
        this.postId = postId;
    }

    public CmdLikeDTO(Integer employeeId, Integer postId) {
        this.employeeId = employeeId;
        this.postId = postId;
    }
}

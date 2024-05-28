package org.triumers.kmsback.post.query.dto;

import lombok.Data;

@Data
public class QryLikeDTO {

    private Integer id;
    private Integer employeeId;
    private Integer postId;

    public QryLikeDTO(Integer id, Integer employeeId, Integer postId) {
        this.id = id;
        this.employeeId = employeeId;
        this.postId = postId;
    }

    public QryLikeDTO(Integer employeeId, Integer postId) {
        this.employeeId = employeeId;
        this.postId = postId;
    }
}

package org.triumers.kmsback.post.query.dto;

import lombok.Data;

@Data
public class QryFavoritesDTO {

    private Integer id;
    private Integer employeeId;
    private Integer postId;

    public QryFavoritesDTO(Integer id, Integer employeeId, Integer postId) {
        this.id = id;
        this.employeeId = employeeId;
        this.postId = postId;
    }

    public QryFavoritesDTO(Integer employeeId, Integer postId) {
        this.employeeId = employeeId;
        this.postId = postId;
    }
}

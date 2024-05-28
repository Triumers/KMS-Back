package org.triumers.kmsback.post.query.dto;

import lombok.Data;

@Data
public class QryTagDTO {
    private Integer id;
    private String name;

    public QryTagDTO() {
    }

    public QryTagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public QryTagDTO(String name) {
        this.name = name;
    }
}

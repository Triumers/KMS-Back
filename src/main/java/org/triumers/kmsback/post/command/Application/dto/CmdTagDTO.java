package org.triumers.kmsback.post.command.Application.dto;

import lombok.Data;

@Data
public class CmdTagDTO {
    private int id;
    private String name;

    public CmdTagDTO() {
    }

    public CmdTagDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CmdTagDTO(String name) {
        this.name = name;
    }
}

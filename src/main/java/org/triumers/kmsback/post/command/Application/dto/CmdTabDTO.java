package org.triumers.kmsback.post.command.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdTabDTO {

    private Integer id;
    private String name;

    public CmdTabDTO(String name) {
        this.name = name;
    }
}

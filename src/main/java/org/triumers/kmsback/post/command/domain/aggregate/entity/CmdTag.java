package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.triumers.kmsback.post.command.Application.dto.CmdTagDTO;

@Entity
@Table(name = "tbl_tag")
@Data
public class CmdTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public CmdTag() {
    }

    public CmdTag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CmdTag(String name) {
        this.name = name;
    }
}

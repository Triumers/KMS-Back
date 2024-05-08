package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}

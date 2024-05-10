package org.triumers.kmsback.post.command.domain.aggregate.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tab_bottom")
@Data
public class CmdTabBottom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
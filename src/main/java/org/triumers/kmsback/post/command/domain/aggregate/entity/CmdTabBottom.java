package org.triumers.kmsback.post.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_tab_bottom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdTabBottom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public CmdTabBottom(String name) {
        this.name = name;
    }
}
package org.triumers.kmsback.user.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_rank")
public class CmdRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;

    @Column(name = "NAME")
    String name;
}

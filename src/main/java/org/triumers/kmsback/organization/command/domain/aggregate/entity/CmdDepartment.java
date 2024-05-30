package org.triumers.kmsback.organization.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tbl_department")
public class CmdDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;

    @Column(name = "NAME")
    String name;

    @Column(name = "CENTER_ID")
    int centerId;
}

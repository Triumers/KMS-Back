package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_like")
@Data
public class CmdLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @Column(name = "POST_ID", nullable = false)
    private Integer postId;

    public CmdLike() {
    }

    public CmdLike(Integer id, Integer employeeId, Integer postId) {
        this.id = id;
        this.employeeId = employeeId;
        this.postId = postId;
    }

    public CmdLike(Integer employeeId, Integer postId) {
        this.employeeId = employeeId;
        this.postId = postId;
    }
}

package org.triumers.kmsback.approval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_approval")
public class CmdApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CONTENT")
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "TYPE_ID", nullable = false)
    private int typeId;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private int employeeId;

    @Column(name = "TAB_ID", nullable = false)
    private int tabId;
}

package org.triumers.kmsback.approval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.triumers.kmsback.employee.command.domain.aggregate.entity.CmdEmployee;
import org.triumers.kmsback.tab.command.domain.aggregate.entity.CmdTabRelation;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID", nullable = false)
    private CmdApprovalType type;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private CmdEmployee requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAB_ID")
    private CmdTabRelation tab = null;
}

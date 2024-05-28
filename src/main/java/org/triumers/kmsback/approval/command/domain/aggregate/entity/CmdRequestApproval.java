package org.triumers.kmsback.approval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_request_approval")
public class CmdRequestApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "APPROVAL_ORDER", nullable = false)
    private int approvalOrder;

    @Column(name = "IS_APPROVED", nullable = false)
    private String isApproved = "WAITING";

    @Column(name = "IS_CANCELED", nullable = false)
    private boolean isCanceled = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Employee approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_ID", nullable = false)
    private CmdApproval approval;
}

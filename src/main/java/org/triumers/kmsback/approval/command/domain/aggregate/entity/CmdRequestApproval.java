package org.triumers.kmsback.approval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    private boolean isApproved = false;

    @Column(name = "IS_CANCELED", nullable = false)
    private boolean isCanceled = false;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private int employeeId;

    @Column(name = "APPROVAL_ID", nullable = false)
    private int approvalId;
}

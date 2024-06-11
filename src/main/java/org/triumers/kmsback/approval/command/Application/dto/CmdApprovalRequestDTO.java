package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdApprovalRequestDTO {
    private String content;
    private int typeId;
    private int requesterId;
    private int approverId;
}
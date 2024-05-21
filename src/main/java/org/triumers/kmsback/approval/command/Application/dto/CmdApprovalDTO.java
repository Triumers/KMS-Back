package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdApprovalDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private int typeId;
    private int employeeId;
    private int tabId;
}

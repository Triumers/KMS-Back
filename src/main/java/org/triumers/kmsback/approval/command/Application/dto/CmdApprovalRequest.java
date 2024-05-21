package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdApprovalRequest {
    private String content;
    private int typeId;
    private int requesterId;
    private List<Integer> approverIds;
    private int tabId;
    private int approvalId;

    public CmdApprovalRequest(String content, int typeId, int requesterId, List<Integer> approverIds, int tabId) {
        this.content = content;
        this.typeId = typeId;
        this.requesterId = requesterId;
        this.approverIds = approverIds;
        this.tabId = tabId;
    }
}

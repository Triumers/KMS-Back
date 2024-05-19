package org.triumers.kmsback.group.query.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryGroupDTO {
    private int employeeId;         // 사원 번호
    private String employeeName;    // 사원 이름
    private int teamId;             // 팀 ID
    private String teamName;        // 팀 이름
    private int departmentId;       // 부서 ID
    private String departmentName;  // 부서 이름
    private int centerId;           // 본부 ID
    private String centerName;      // 본부 이름
    private int tabId;              // 탭간 관계 ID
    private int topTabId;           // 상위 탭 ID
    private String topTabName;      // 상위 탭 이름
    private int bottomTabId;        // 하위 탭 ID
    private String bottomTabName;   // 하위 탭 이름
    private boolean isPublic;       // 공개 여부
    private boolean isLeader;       // 리더 여부
}

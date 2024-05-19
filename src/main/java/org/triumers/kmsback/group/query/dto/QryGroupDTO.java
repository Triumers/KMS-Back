package org.triumers.kmsback.group.query.dto;

import lombok.*;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QryGroupDTO that = (QryGroupDTO) o;
        return employeeId == that.employeeId &&
                teamId == that.teamId &&
                departmentId == that.departmentId &&
                centerId == that.centerId &&
                tabId == that.tabId &&
                topTabId == that.topTabId &&
                bottomTabId == that.bottomTabId &&
                isPublic == that.isPublic &&
                isLeader == that.isLeader &&
                Objects.equals(employeeName, that.employeeName) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(centerName, that.centerName) &&
                Objects.equals(topTabName, that.topTabName) &&
                Objects.equals(bottomTabName, that.bottomTabName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, teamId, teamName, departmentId, departmentName, centerId, centerName, tabId, topTabId, topTabName, bottomTabId, bottomTabName, isPublic, isLeader);
    }
}

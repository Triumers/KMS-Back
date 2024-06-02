package org.triumers.kmsback.organization.query.aggregate.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryDepartmentVO {
    int id;
    String name;
    List<QryTeamVO> belongTeams;
}

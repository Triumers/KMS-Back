package org.triumers.kmsback.organization.query.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryDepartmentDTO {
    int id;
    String name;
    int centerId;
    String centerName;
    List<QryTeamDTO> belongTeams;
}

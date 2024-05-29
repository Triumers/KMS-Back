package org.triumers.kmsback.organization.query.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryTeamDTO {
    int id;
    String name;
    int departmentId;
    String departmentName;
}

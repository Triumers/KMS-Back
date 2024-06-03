package org.triumers.kmsback.organization.query.dto;

import lombok.*;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

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
    List<QryEmployeeDTO> teamMembers;
}

package org.triumers.kmsback.organization.query.dto;

import lombok.*;

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
}

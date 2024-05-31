package org.triumers.kmsback.organization.query.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryCenterDTO {
    int id;
    String name;
    List<QryDepartmentDTO> belongDepartments;
}

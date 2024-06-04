package org.triumers.kmsback.user.query.aggregate.vo;

import lombok.*;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryResponseEmployeeVO {
    private String message;
    List<QryEmployeeDTO> employee;
}

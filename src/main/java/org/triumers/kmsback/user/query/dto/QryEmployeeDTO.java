package org.triumers.kmsback.user.query.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryEmployeeDTO {
    private int id;
    private String email;
    private String name;
    private String profileImg;
    private LocalDate startDate;
    private LocalDate endDate;
    private String phoneNumber;
    private Map<String, String> team;
    private Map<String, String> position;
    private Map<String, String> rank;
}

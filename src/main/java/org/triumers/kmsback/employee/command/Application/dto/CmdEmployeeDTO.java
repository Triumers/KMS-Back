package org.triumers.kmsback.employee.command.Application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdEmployeeDTO {
    private int id;
    private String email;
    private String name;
    private String profileImg;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String phoneNumber;
    private int teamId;
    private int positionId;
    private int rankId;
}

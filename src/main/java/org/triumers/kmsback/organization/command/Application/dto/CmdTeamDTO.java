package org.triumers.kmsback.organization.command.Application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdTeamDTO {
    int id;
    String name;
    int departmentId;
}

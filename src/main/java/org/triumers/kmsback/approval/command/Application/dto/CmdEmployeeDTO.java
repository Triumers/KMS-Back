package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdEmployeeDTO {

    private int id;
    private String name;
    private String profileImg;
    private String centerName;
    private String departmentName;
    private String teamName;
    private String positionName;
    private String rankName;

}

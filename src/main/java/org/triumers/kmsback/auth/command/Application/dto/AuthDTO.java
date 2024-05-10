package org.triumers.kmsback.auth.command.Application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter// Git 충돌 복구용 주석
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthDTO {
    private String email;
    private String password;
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
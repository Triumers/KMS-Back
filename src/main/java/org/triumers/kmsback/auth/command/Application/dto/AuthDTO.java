package org.triumers.kmsback.auth.command.Application.dto;

import lombok.*;

@Getter
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
}
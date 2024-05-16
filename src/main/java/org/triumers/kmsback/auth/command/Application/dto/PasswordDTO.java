package org.triumers.kmsback.auth.command.Application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PasswordDTO {
    private String oldPassword;
    private String newPassword;
}

package org.triumers.kmsback.user.command.Application.dto;

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

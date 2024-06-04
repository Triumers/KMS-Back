package org.triumers.kmsback.user.command.domain.aggregate.vo;

import lombok.Getter;

@Getter
public class CmdRequestAccountVO {
    private String email;
    private String password;
}

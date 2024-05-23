package org.triumers.kmsback.auth.command.domain.aggregate.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.triumers.kmsback.common.util.RegularExpression;

@Getter
public class CmdRequestEditPasswordVO {

    @NotBlank(message = "이전 비밀번호는 필수 기입 정보입니다.")
    private String oldPassword;

    @NotBlank(message = "변경할 비밀번호는 필수 기입 정보입니다.")
    @Pattern(regexp = RegularExpression.PASSWORD,
             message = "비밀번호는 8~12자리의 숫자, 소문자, 대문자를 각각 한 개 이상 가져야 합니다.")
    private String newPassword;
}

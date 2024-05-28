package org.triumers.kmsback.user.command.domain.aggregate.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.triumers.kmsback.common.util.RegularExpression;

import java.time.LocalDate;

@Getter
public class CmdRequestEditUserVO {

    @NotBlank(message = "수정할 회원의 이메일을 입력해주세요.")
    private String email;

    private String password;

    @Pattern(regexp = RegularExpression.NAME,
            message = "이름은 한글, 영어로만 조합해야 합니다.")
    private String name;

    @Pattern(regexp = RegularExpression.PHONE_NUMBER)
    private String phoneNumber;

    private String role;
    private String profileImg;
    private LocalDate startDate;
    private LocalDate endDate;
    private int teamId;
    private int positionId;
    private int rankId;
}

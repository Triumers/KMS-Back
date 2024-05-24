package org.triumers.kmsback.user.command.domain.aggregate.vo;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.triumers.kmsback.common.util.RegularExpression;

@Getter
public class CmdRequestEditMyInfoVO {

    @Pattern(regexp = RegularExpression.NAME,
             message = "이름은 한글, 영어로만 조합해야 합니다.")
    private String name;

    @Pattern(regexp = RegularExpression.PHONE_NUMBER,
             message = "010-0000-0000 형식으로 입력해야 합니다.")
    private String phoneNumber;

    private String profileImg;
}

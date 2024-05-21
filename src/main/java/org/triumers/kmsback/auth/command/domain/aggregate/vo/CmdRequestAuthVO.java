package org.triumers.kmsback.auth.command.domain.aggregate.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.triumers.kmsback.common.util.RegularExpression;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdRequestAuthVO {

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 기입 정보입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 기입 정보입니다.")
    @Pattern(regexp = RegularExpression.PASSWORD,
             message = "비밀번호는 8~12자리의 숫자, 소문자, 대문자를 각각 한 개 이상 가져야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 기입 정보입니다.")
    @Pattern(regexp = RegularExpression.NAME,
             message = "이름은 한글, 영어로만 조합해야 합니다.")
    private String name;

    @Pattern(regexp = RegularExpression.PHONE_NUMBER)
    private String phoneNumber;

    @NotBlank(message = "회원의 권한을 설정해주세요.")
    private String role;

    private String profileImg;
    private LocalDate startDate;
    private LocalDate endDate;
    private int teamId;
    private int positionId;
    private int rankId;
}

package org.triumers.kmsback.auth.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.auth.command.domain.aggregate.enums.UserRoleConverter;
import org.triumers.kmsback.common.exception.WrongInputTypeException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_employee")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROFILE_IMG")
    private String profileImg;

    @Column(name = "ROLE")
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    @CreatedDate
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Column(name = "TEAM_ID")
    private int teamId;

    @Column(name = "POSITION_ID")
    private int positionId;

    @Column(name = "RANK_ID")
    private int rankId;

    public void validationWithoutPassword() throws WrongInputTypeException {
        emailValidation();
        nameValidation();
        phoneNumberValidation();
    }

    public void validationAll(String password) throws WrongInputTypeException {
        emailValidation();
        passwordValidation(password);
        nameValidation();
        phoneNumberValidation();
    }

    private void emailValidation() throws WrongInputTypeException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");

        Matcher matcher = pattern.matcher(this.email);

        if (!matcher.matches()) {
            throw new WrongInputTypeException();
        }
    }

    private void passwordValidation(String password) throws WrongInputTypeException {

        if (password == null) {
            return;
        }

        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,12}$");

        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new WrongInputTypeException();
        }
    }

    private void nameValidation() throws WrongInputTypeException {
        Pattern pattern = Pattern.compile("^[가-힣a-zA-Z]+$");

        Matcher matcher = pattern.matcher(this.name);

        if (!matcher.matches()) {
            throw new WrongInputTypeException();
        }
    }

    private void phoneNumberValidation() throws WrongInputTypeException {
        if (phoneNumber == null) {
            return;
        }
        Pattern pattern = Pattern.compile("^010-?([0-9]{3,4})-?([0-9]{4})$");

        Matcher matcher = pattern.matcher(this.phoneNumber);

        if (!matcher.matches()) {
            throw new WrongInputTypeException();
        }
    }
}

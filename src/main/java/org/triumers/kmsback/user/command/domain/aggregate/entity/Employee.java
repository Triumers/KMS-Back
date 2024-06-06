package org.triumers.kmsback.user.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRoleConverter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE tbl_employee SET END_DATE = CURRENT_DATE WHERE ID = ?")
@Where(clause = "END_DATE IS NULL")
@Table(name = "tbl_employee")
public class Employee {

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

    @Column(name = "GOOGLE_AUTH_KEY")
    private String googleAuthKey;

    @Column(name = "TEAM_ID")
    private int teamId;

    @Column(name = "POSITION_ID")
    private int positionId;

    @Column(name = "RANK_ID")
    private int rankId;
}

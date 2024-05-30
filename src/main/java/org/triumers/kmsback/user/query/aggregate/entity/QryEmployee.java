package org.triumers.kmsback.user.query.aggregate.entity;

import lombok.*;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryEmployee {

    private int id;
    private String email;
    private String name;
    private String profileImg;
    private UserRole userRole;
    private LocalDate startDate;
    private LocalDate endDate;
    private String phoneNumber;
    private int teamId;
    private int positionId;
    private int rankId;
}

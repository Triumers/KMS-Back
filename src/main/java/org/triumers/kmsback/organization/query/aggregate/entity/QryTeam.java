package org.triumers.kmsback.organization.query.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryTeam {
    int id;
    String name;
    int departmentId;
}

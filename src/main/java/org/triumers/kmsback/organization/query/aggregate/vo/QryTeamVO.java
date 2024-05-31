package org.triumers.kmsback.organization.query.aggregate.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryTeamVO {
    int id;
    String name;
    List<QryTeamMemberVO> members;
}

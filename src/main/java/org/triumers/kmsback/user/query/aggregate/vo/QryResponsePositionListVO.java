package org.triumers.kmsback.user.query.aggregate.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryResponsePositionListVO {
    private List<QryPositionVO> position;
}

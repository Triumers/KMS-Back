package org.triumers.kmsback.user.query.aggregate.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryResponseRankListVO {
    private List<QryRankVO> rank;
}
